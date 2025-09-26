package com.vitatrack.app.ui.meal

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.vitatrack.app.R
import com.vitatrack.app.VitaTrackApplication
import com.vitatrack.app.data.dao.MealDao
import com.vitatrack.app.data.model.MealType
import com.vitatrack.app.databinding.FragmentMealBinding

class MealFragment : Fragment() {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var mealViewModel: MealTrackingViewModel
    private lateinit var auth: FirebaseAuth

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
        val mealDao = (requireActivity().application as VitaTrackApplication).database.mealDao()
        mealViewModel = ViewModelProvider(this, MealTrackingViewModelFactory(mealDao))[MealTrackingViewModel::class.java]
        
        setupClickListeners()
        observeViewModel()
        loadMealData()
    }

    private fun setupClickListeners() {
        binding.fabAddMeal.setOnClickListener {
            showAddMealDialog()
        }
        
        binding.btnAddFirstMeal.setOnClickListener {
            showAddMealDialog()
        }
    }

    private fun showAddMealDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_meal, null)
        
        val etMealName = dialogView.findViewById<EditText>(R.id.etMealName)
        val spinnerMealType = dialogView.findViewById<Spinner>(R.id.spinnerMealType)
        val etCalories = dialogView.findViewById<EditText>(R.id.etCalories)
        val etProtein = dialogView.findViewById<EditText>(R.id.etProtein)
        val etCarbs = dialogView.findViewById<EditText>(R.id.etCarbs)
        val etFat = dialogView.findViewById<EditText>(R.id.etFat)
        
        // Setup spinner
        val mealTypes = MealType.values().map { it.name.lowercase().replaceFirstChar { char -> char.uppercase() } }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mealTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMealType.adapter = adapter

        AlertDialog.Builder(requireContext())
            .setTitle("Add Meal")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etMealName.text.toString().trim()
                val calories = etCalories.text.toString().toIntOrNull() ?: 0
                val protein = etProtein.text.toString().toFloatOrNull()
                val carbs = etCarbs.text.toString().toFloatOrNull()
                val fat = etFat.text.toString().toFloatOrNull()
                val selectedType = MealType.values()[spinnerMealType.selectedItemPosition]
                
                if (name.isNotEmpty() && calories > 0) {
                    addMeal(name, selectedType, calories, protein, carbs, fat)
                } else {
                    Toast.makeText(context, "Please enter meal name and calories", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addMeal(name: String, type: MealType, calories: Int, protein: Float?, carbs: Float?, fat: Float?) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            mealViewModel.addMeal(currentUser.uid, name, type, calories, protein, carbs, fat)
            Toast.makeText(context, "Meal added successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        mealViewModel.todayMeals.observe(viewLifecycleOwner) { meals ->
            updateMealsList(meals.size)
        }
        
        mealViewModel.dailyCalories.observe(viewLifecycleOwner) { calories ->
            binding.tvTodayCalories.text = "Today: $calories calories"
        }
        
        mealViewModel.dailyProtein.observe(viewLifecycleOwner) { protein ->
            binding.tvProtein.text = "${protein.toInt()}g"
        }
        
        mealViewModel.dailyCarbs.observe(viewLifecycleOwner) { carbs ->
            binding.tvCarbs.text = "${carbs.toInt()}g"
        }
        
        mealViewModel.dailyFat.observe(viewLifecycleOwner) { fat ->
            binding.tvFat.text = "${fat.toInt()}g"
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
            mealViewModel.loadTodayMeals(currentUser.uid)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
