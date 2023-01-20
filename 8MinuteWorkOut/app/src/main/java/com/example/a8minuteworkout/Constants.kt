package com.example.a8minuteworkout

object Constants {
    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val exercise1=ExerciseModel(
            1,"Jumping Jack",R.drawable.ic_jumping_jacks,false,false
        )
        exerciseList.add(exercise1)

        val exercise2=ExerciseModel(
            2,"High Knees",R.drawable.ic_high_knees_running_in_place,false,
            false
        )
        exerciseList.add(exercise2)
        val exercise3=ExerciseModel(
            3,"Lunge",R.drawable.ic_lunge,false,
            false
        )
        exerciseList.add(exercise3)
        val exercise4=ExerciseModel(
            4,"Plank",R.drawable.ic_plank,false,
            false
        )
        exerciseList.add(exercise4)

        val exercise5=ExerciseModel(
            5,"Push up",R.drawable.ic_push_up,false,
            false
        )
        exerciseList.add(exercise5)
        val exercise6=ExerciseModel(
            6,"Push up and Rotation",R.drawable.ic_push_up_and_rotation,false,
            false
        )
        exerciseList.add(exercise6)

        val exercise7=ExerciseModel(
            7,"Side Plank",R.drawable.ic_side_plank,false,
            false
        )
        exerciseList.add(exercise7)
        val exercise8=ExerciseModel(
            8,"Squart",R.drawable.ic_squat,false,
            false
        )
        exerciseList.add(exercise8)
        val exercise9=ExerciseModel(
            9,"Step up onto Chair",R.drawable.ic_step_up_onto_chair,false,
            false
        )
        exerciseList.add(exercise9)
        val exercise10=ExerciseModel(
            10,"Triceps dip on chair",R.drawable.ic_triceps_dip_on_chair,false,
            false
        )
        exerciseList.add(exercise10)

        val exercise11=ExerciseModel(
            11,"Wall sit",R.drawable.ic_wall_sit,false,
            false
        )
        exerciseList.add(exercise11)

        return exerciseList
    }
}