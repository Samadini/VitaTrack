package com.vitatrack.app.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\t\u0010\u0004\u001a\u00020\u0005H\u00d6\u0001J\u0019\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u00d6\u0001j\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/vitatrack/app/data/model/ExerciseType;", "", "Landroid/os/Parcelable;", "(Ljava/lang/String;I)V", "describeContents", "", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "WALKING", "RUNNING", "CYCLING", "SWIMMING", "YOGA", "GYM", "CARDIO", "STRENGTH", "SPORTS", "OTHER", "app_debug"})
@kotlinx.parcelize.Parcelize()
public enum ExerciseType implements android.os.Parcelable {
    /*public static final*/ WALKING /* = new WALKING() */,
    /*public static final*/ RUNNING /* = new RUNNING() */,
    /*public static final*/ CYCLING /* = new CYCLING() */,
    /*public static final*/ SWIMMING /* = new SWIMMING() */,
    /*public static final*/ YOGA /* = new YOGA() */,
    /*public static final*/ GYM /* = new GYM() */,
    /*public static final*/ CARDIO /* = new CARDIO() */,
    /*public static final*/ STRENGTH /* = new STRENGTH() */,
    /*public static final*/ SPORTS /* = new SPORTS() */,
    /*public static final*/ OTHER /* = new OTHER() */;
    
    ExerciseType() {
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.vitatrack.app.data.model.ExerciseType> getEntries() {
        return null;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
}