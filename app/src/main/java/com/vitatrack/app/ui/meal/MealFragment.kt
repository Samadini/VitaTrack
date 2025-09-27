package com.vitatrack.app.ui.meal

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.data.model.FoodItem
import com.vitatrack.app.data.model.Meal
import com.vitatrack.app.data.model.MealType
import com.vitatrack.app.databinding.FragmentMealBinding
import com.vitatrack.app.databinding.DialogAddFoodItemBinding
import com.vitatrack.app.databinding.DialogCreateMealBinding
import com.vitatrack.app.ui.meal.adapter.FoodItemAdapter
import com.vitatrack.app.ui.meal.adapter.MealAdapter
import com.vitatrack.app.data.nutrition.NutritionDatabase
import com.vitatrack.app.ui.base.BaseFragment
import android.text.Editable
import android.text.TextWatcher
import java.text.SimpleDateFormat
import java.util.*

class MealFragment : BaseFragment() {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var mealCrudViewModel: MealCrudViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var mealAdapter: MealAdapter
    private lateinit var foodItemAdapter: FoodItemAdapter
    
    private var currentMealId: String? = null
    private var isCreatingMeal = false
    private var selectedDate: Date = Date() // Default to today
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        auth = FirebaseAuth.getInstance()
        
        // Initialize ViewModel
        val app = requireActivity().application as VitaTrackApplication
        val mealDao = app.database.mealDao()
        val foodItemDao = app.database.foodItemDao()
        val repository = app.mealRepository
        
        mealCrudViewModel = ViewModelProvider(
            this, 
            MealCrudViewModelFactory(repository)
        )[MealCrudViewModel::class.java]
        
        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()
        updateNutritionTitle()
        loadMealData()
    }

    private fun setupRecyclerViews() {
        // Setup meal adapter
        mealAdapter = MealAdapter(
            onMealClick = { meal -> 
                // Navigate to meal detail or show food items
                showMealDetails(meal)
            },
            onEditClick = { meal -> 
                showEditMealDialog(meal)
            },
            onDeleteClick = { meal -> 
                showDeleteMealConfirmation(meal)
            }
        )
        
        binding.rvMeals.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mealAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAddMeal.setOnClickListener {
            showCreateMealDialog()
        }

        binding.btnAddFirstMeal.setOnClickListener {
            showCreateMealDialog()
        }

        binding.btnSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showCreateMealDialog() {
        val dialogBinding = DialogCreateMealBinding.inflate(layoutInflater)
        
        // Setup meal type dropdown
        val mealTypes = MealType.values().map { getMealTypeDisplayName(it) }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_generic, mealTypes)
        dialogBinding.spinnerMealType.setAdapter(adapter)
        dialogBinding.spinnerMealType.setText(mealTypes[0], false)
        
        // Make dropdown clickable and show on click
        dialogBinding.spinnerMealType.setOnClickListener {
            dialogBinding.spinnerMealType.showDropDown()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Create New Meal")
            .setView(dialogBinding.root)
            .setPositiveButton("Create") { _, _ ->
                val name = dialogBinding.etMealName.text.toString().trim()
                val typeIndex = mealTypes.indexOf(dialogBinding.spinnerMealType.text.toString())
                val selectedType = if (typeIndex >= 0) MealType.values()[typeIndex] else MealType.OTHER
                val notes = dialogBinding.etNotes.text.toString().trim().ifEmpty { null }
                
                if (name.isNotEmpty()) {
                    createMeal(name, selectedType, notes)
                } else {
                    Toast.makeText(context, "Please enter meal name", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun createMeal(name: String, type: MealType, notes: String?) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentMealId = mealCrudViewModel.createMeal(currentUser.uid, name, type, notes)
            isCreatingMeal = true
            showAddFoodItemsDialog()
        }
    }

    private fun showAddFoodItemsDialog() {
        val dialogBinding = DialogAddFoodItemBinding.inflate(layoutInflater)
        
        // Setup food name autocomplete
        val foodNames = NutritionDatabase.getAllFoodNames()
        val foodAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_generic, foodNames)
        dialogBinding.etFoodName.setAdapter(foodAdapter)
        
        // Add text watcher for automatic nutrition calculation
        dialogBinding.etFoodName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateAndUpdateNutrition(dialogBinding)
            }
        })
        
        dialogBinding.etQuantity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateAndUpdateNutrition(dialogBinding)
            }
        })
        
        // Set default values
        dialogBinding.etQuantity.setText("100")
        dialogBinding.etUnit.setText("g")

        AlertDialog.Builder(requireContext())
            .setTitle("Add Food Item")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val foodName = dialogBinding.etFoodName.text.toString().trim()
                val quantity = dialogBinding.etQuantity.text.toString().toFloatOrNull() ?: 100f
                val unit = dialogBinding.etUnit.text.toString().trim().ifEmpty { "g" }
                val calories = dialogBinding.etCalories.text.toString().toIntOrNull() ?: 0
                val protein = dialogBinding.etProtein.text.toString().toFloatOrNull() ?: 0f
                val carbs = dialogBinding.etCarbs.text.toString().toFloatOrNull() ?: 0f
                val fat = dialogBinding.etFat.text.toString().toFloatOrNull() ?: 0f
                
                if (foodName.isNotEmpty() && calories > 0) {
                    val foodItem = FoodItem(
                        id = UUID.randomUUID().toString(),
                        mealId = currentMealId ?: "",
                        name = foodName,
                        calories = calories,
                        protein = protein,
                        carbs = carbs,
                        fat = fat,
                        quantity = quantity,
                        unit = unit
                    )
                    mealCrudViewModel.addFoodItemToTemp(foodItem)
                    
                    // Show option to add more items or save meal
                    showMealSummaryDialog()
                } else {
                    Toast.makeText(context, "Please enter food name", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                if (isCreatingMeal) {
                    // If creating meal, show summary to save or cancel
                    showMealSummaryDialog()
                }
            }
            .show()
    }
    
    private fun calculateAndUpdateNutrition(dialogBinding: DialogAddFoodItemBinding) {
        val foodName = dialogBinding.etFoodName.text.toString().trim()
        val quantityText = dialogBinding.etQuantity.text.toString()
        val quantity = quantityText.toFloatOrNull() ?: 100f
        
        if (foodName.isNotEmpty()) {
            val nutritionInfo = NutritionDatabase.calculateNutrition(foodName, quantity)
            if (nutritionInfo != null) {
                dialogBinding.etCalories.setText(nutritionInfo.caloriesPer100g.toString())
                dialogBinding.etProtein.setText(String.format("%.1f", nutritionInfo.proteinPer100g))
                dialogBinding.etCarbs.setText(String.format("%.1f", nutritionInfo.carbsPer100g))
                dialogBinding.etFat.setText(String.format("%.1f", nutritionInfo.fatPer100g))
            }
        }
    }

    private fun showMealSummaryDialog() {
        val tempFoodItems = mealCrudViewModel.tempFoodItems.value ?: emptyList()
        val (totalCalories, totalProtein, macros) = mealCrudViewModel.getTempMealNutrition()
        val (totalCarbs, totalFat, _) = macros
        
        val message = buildString {
            append("Meal Summary:\n\n")
            append("Food Items (${tempFoodItems.size}):\n")
            tempFoodItems.forEach { item ->
                append("• ${item.name} (${item.quantity} ${item.unit})\n")
            }
            append("\nTotal Nutrition:\n")
            append("Calories: ${totalCalories}\n")
            append("Protein: ${String.format("%.1f", totalProtein)}g\n")
            append("Carbs: ${String.format("%.1f", totalCarbs)}g\n")
            append("Fat: ${String.format("%.1f", totalFat)}g")
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle("Meal Summary")
            .setMessage(message)
            .setPositiveButton("Add More Food") { _, _ ->
                showAddFoodItemsDialog()
            }
            .setNeutralButton("Save Meal") { _, _ ->
                currentMealId?.let { mealId ->
                    mealCrudViewModel.saveMealWithFoodItems(mealId)
                    Toast.makeText(context, "Meal saved successfully!", Toast.LENGTH_SHORT).show()
                    resetMealCreation()
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                resetMealCreation()
            }
            .show()
    }
    
    private fun resetMealCreation() {
        currentMealId = null
        isCreatingMeal = false
        mealCrudViewModel.clearCurrentMeal()
    }

    private fun showMealDetails(meal: Meal) {
        mealCrudViewModel.loadMealById(meal.id)
        // This could navigate to a detailed meal view or show food items in a dialog
        Toast.makeText(context, "Viewing meal: ${meal.name}", Toast.LENGTH_SHORT).show()
    }

    private fun showEditMealDialog(meal: Meal) {
        val dialogBinding = DialogCreateMealBinding.inflate(layoutInflater)
        
        // Pre-fill with existing data
        dialogBinding.etMealName.setText(meal.name)
        dialogBinding.etNotes.setText(meal.notes ?: "")
        
        // Setup meal type dropdown
        val mealTypes = MealType.values().map { getMealTypeDisplayName(it) }
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_generic, mealTypes)
        dialogBinding.spinnerMealType.setAdapter(adapter)
        dialogBinding.spinnerMealType.setText(getMealTypeDisplayName(meal.type), false)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Meal")
            .setView(dialogBinding.root)
            .setPositiveButton("Update") { _, _ ->
                val name = dialogBinding.etMealName.text.toString().trim()
                val typeIndex = mealTypes.indexOf(dialogBinding.spinnerMealType.text.toString())
                val selectedType = if (typeIndex >= 0) MealType.values()[typeIndex] else meal.type
                val notes = dialogBinding.etNotes.text.toString().trim().ifEmpty { null }
                
                if (name.isNotEmpty()) {
                    val updatedMeal = meal.copy(
                        name = name,
                        type = selectedType,
                        notes = notes
                    )
                    mealCrudViewModel.updateMeal(updatedMeal)
                    Toast.makeText(context, "Meal updated successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter meal name", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteMealConfirmation(meal: Meal) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Meal")
            .setMessage("Are you sure you want to delete '${meal.name}'? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                mealCrudViewModel.deleteMeal(meal)
                Toast.makeText(context, "Meal deleted successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun getMealTypeDisplayName(type: MealType): String {
        return when (type) {
            MealType.BREAKFAST -> "Breakfast"
            MealType.LUNCH -> "Lunch"
            MealType.DINNER -> "Dinner"
            MealType.SNACK -> "Snack"
            MealType.OTHER -> "Other"
        }
    }

    private fun observeViewModel() {
        mealCrudViewModel.meals.observe(viewLifecycleOwner) { meals ->
            mealAdapter.submitList(meals)
            updateMealsList(meals.size)
        }
        
        mealCrudViewModel.dailyCalories.observe(viewLifecycleOwner) { calories ->
            binding.tvTodayCalories.text = "Today: $calories calories"
        }
        
        mealCrudViewModel.dailyProtein.observe(viewLifecycleOwner) { protein ->
            binding.tvProtein.text = "${protein.toInt()}g"
        }
        
        mealCrudViewModel.dailyCarbs.observe(viewLifecycleOwner) { carbs ->
            binding.tvCarbs.text = "${carbs.toInt()}g"
        }
        
        mealCrudViewModel.dailyFat.observe(viewLifecycleOwner) { fat ->
            binding.tvFat.text = "${fat.toInt()}g"
        }
        
        mealCrudViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                mealCrudViewModel.clearError()
            }
        }
        
        mealCrudViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // You could show/hide a progress indicator here
            binding.fabAddMeal.isEnabled = !isLoading
        }
    }

    private fun updateMealsList(mealCount: Int) {
        if (mealCount > 0) {
            binding.emptyStateMeal.visibility = View.GONE
            binding.rvMeals.visibility = View.VISIBLE
        } else {
            binding.emptyStateMeal.visibility = View.VISIBLE
            binding.rvMeals.visibility = View.GONE
        }
    }

    private fun loadMealData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            mealCrudViewModel.loadMealsByDate(currentUser.uid, selectedDate)
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val newCalendar = Calendar.getInstance()
                newCalendar.set(year, month, dayOfMonth)
                selectedDate = newCalendar.time
                updateNutritionTitle()
                loadMealData()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        
        datePickerDialog.show()
    }

    private fun updateNutritionTitle() {
        val today = Calendar.getInstance()
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.time = selectedDate
        
        val title = if (isSameDay(today, selectedCalendar)) {
            "Today's Nutrition"
        } else {
            "${dateFormatter.format(selectedDate)} Nutrition"
        }
        
        binding.tvNutritionTitle.text = title
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
