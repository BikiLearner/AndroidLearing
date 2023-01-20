package com.example.a8minuteworkout

class ExerciseModel(
    private var id:Int,
   private var exerciseName:String,
    private var exerciseImg:Int,
    private var isComplete:Boolean,
    private var isSelected:Boolean
) {
    fun getId():Int{
        return id
    }
    fun setId(id: Int){
        this.id=id
    }
    fun getExerciseName():String{
        return exerciseName
    }
    fun setExerciseName(exerciseName: String){
        this.exerciseName=exerciseName
    }
    fun getExerciseImg():Int{
        return exerciseImg
    }
    fun setExerciseImg(exerciseImg: Int){
        this.exerciseImg=exerciseImg
    }
    fun getIsComplete():Boolean{
        return isComplete
    }
    fun setIsComplete(isComplete:Boolean){
        this.isComplete=isComplete
    }
    fun getIsSelected():Boolean{
        return isSelected
    }
    fun setIsSelected(isSelected: Boolean){
        this.isSelected=isSelected
    }

}