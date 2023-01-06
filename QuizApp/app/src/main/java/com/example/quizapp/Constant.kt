package com.example.quizapp

object Constant {

const val USER_NAME : String="User_name"
const val TOTAL_QUESTION : String="total_question"
const val CORRECT_ANSWER : String="correct_answer"

    fun getQuestion():ArrayList<Questions>{

        val questionsList = ArrayList<Questions>()

        // 1
        val que1 = Questions(
            1, "What country does this flag belong to?",
            R.drawable.ic_flag_of_argentina,
            "Argentina", "Australia",
            "Armenia", "Austria", 1
        )

        questionsList.add(que1)

        // 2
        val que2 = Questions(
            2, "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            "Angola", "Austria",
            "Australia", "Armenia", 3
        )

        questionsList.add(que2)

        // 3
        val que3 = Questions(
            3, "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            "Belarus", "Belize",
            "Brunei", "Brazil", 4
        )

        questionsList.add(que3)

        // 4
        val que4 = Questions(
            4, "What country does this flag belong to?",
            R.drawable.ic_flag_of_belgium,
            "Bahamas", "Belgium",
            "Barbados", "Belize", 2
        )

        questionsList.add(que4)

        // 5
        val que5 = Questions(
            5, "What country does this flag belong to?",
            R.drawable.ic_flag_of_fiji,
            "Gabon", "France",
            "Fiji", "Finland", 3
        )

        questionsList.add(que5)

        // 6
        val que6 = Questions(
            6, "What country does this flag belong to?",
            R.drawable.ic_flag_of_germany,
            "Germany", "Georgia",
            "Greece", "none of these", 1
        )

        questionsList.add(que6)

        // 7
        val que7 = Questions(
            7, "What country does this flag belong to?",
            R.drawable.ic_flag_of_denmark,
            "Dominica", "Egypt",
            "Denmark", "Ethiopia", 3
        )

        questionsList.add(que7)

        // 8
        val que8 = Questions(
            8, "What country does this flag belong to?",
            R.drawable.ic_flag_of_india,
            "Ireland", "Iran",
            "Hungary", "India", 4
        )

        questionsList.add(que8)

        // 9
        val que9 = Questions(
            9, "What country does this flag belong to?",
            R.drawable.ic_flag_of_new_zealand,
            "Australia", "New Zealand",
            "Tuvalu", "United States of America", 2
        )

        questionsList.add(que9)

        // 10
        val que10 = Questions(
            10, "What country does this flag belong to?",
            R.drawable.ic_flag_of_kuwait,
            "Kuwait", "Jordan",
            "Sudan", "Palestine", 1
        )

        questionsList.add(que10)

        return questionsList
    }
    fun javaQuestion():ArrayList<QuestionsNoImage>{
        val questionsList2 = ArrayList<QuestionsNoImage>()

        var que1=QuestionsNoImage(1,
            "Which Set class should be most popular in a multi-threading environment, considering performance constraint?",
            "HashSet"," ConcurrentSkipListSet","LinkedHashSet", " CopyOnWriteArraySet",
            2

        )
        questionsList2.add(que1)
        var que2=QuestionsNoImage(2,
            "Which Map class should be most popular in a multi-threading environment, considering performance constraint?",
            "Hashtable","CopyOnWriteMap"," ConcurrentHashMap", "ConcurrentMap",
            2

        )
        questionsList2.add(que2)
        var que3=QuestionsNoImage(3,
            "Which allows the removal of elements from a collection?",
            " Enumeration","Iterator","Both", "None of the above",
            4

        )
        questionsList2.add(que3)



        return questionsList2
    }
}