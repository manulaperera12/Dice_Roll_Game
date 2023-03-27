package com.example.dicerollapp

import android.content.Context
import android.content.RestrictionEntry.TYPE_NULL
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.dicerollapp.R.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.score_popup.view.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var rollText: TextView
    private lateinit var rollText2: TextView
    private lateinit var playerResultImage1: ImageView
    private lateinit var playerResultImage2: ImageView
    private lateinit var playerResultImage3: ImageView
    private lateinit var playerResultImage4: ImageView
    private lateinit var playerResultImage5: ImageView
    private lateinit var computerResultImage1: ImageView
    private lateinit var computerResultImage2: ImageView
    private lateinit var computerResultImage3: ImageView
    private lateinit var computerResultImage4: ImageView
    private lateinit var computerResultImage5: ImageView
    private lateinit var textInput: TextInputLayout
    private lateinit var targetTextView: TextView
    private lateinit var new: TextView
    private lateinit var new2: TextView


    private  var value: Int = 101
    private lateinit var editText: EditText
    private lateinit var rollButton: Button
    private val clickedImages = mutableListOf<Int>()
    private var dicesValuePlayer = mutableListOf<Int>()
    private var dicesValueComputer = mutableListOf<Int>()
    private var score1 = 0
    private var score2 = 0
    private var total = 0
    private var humanTotalWins = 0
    private var count = 0
    private var playerCurrentRollTotal = 0
    private var computerCurrentRollTotal = 0
    private  var totalScoreText1 = ""
    private  var totalScoreText2 = ""
    private var playerSum = 0
    private var computerSum = 0
    private val computerRandomInt1 = (1..6).random()
    private val computerRandomInt2 = (1..6).random()
    private val computerRandomInt3 = (1..6).random()
    private val computerRandomInt4 = (1..6).random()
    private val computerRandomInt5 = (1..6).random()
    private val computerDrawableImage1 = updateImage(computerRandomInt4)
    private val computerDrawableImage2 = updateImage(computerRandomInt2)
    private val computerDrawableImage3 = updateImage(computerRandomInt5)
    private val computerDrawableImage4 = updateImage(computerRandomInt1)
    private val computerDrawableImage5 = updateImage(computerRandomInt2)
    private val playerTotalWins = mutableListOf<Int>()
    private val computerTotalWins = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        eachRoundWins()
        val prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("myListSize", playerTotalWins.size)
        for (i in playerTotalWins.indices) {
            editor.putInt("myList$i", playerTotalWins[i])
        }
        editor.apply()

        playerTotalScore.text = "H: ${playerTotalWins.size}"
        computerTotalScore.text = "C: ${computerTotalWins.size}"

        rollText = findViewById(id.throwText)
        rollText2 = findViewById(id.throwText2)
        playerResultImage1 = findViewById(id.playerDiceImage1)
        playerResultImage2 = findViewById(id.playerDiceImage2)
        playerResultImage3 = findViewById(id.playerDiceImage3)
        playerResultImage4 = findViewById(id.playerDiceImage4)
        playerResultImage5 = findViewById(id.playerDiceImage5)
        computerResultImage1 = findViewById(id.computerDiceImage1)
        computerResultImage2 = findViewById(id.computerDiceImage2)
        computerResultImage3 = findViewById(id.computerDiceImage3)
        computerResultImage4 = findViewById(id.computerDiceImage4)
        computerResultImage5 = findViewById(id.computerDiceImage5)
        rollButton = findViewById(id.rollButton)
        textInput = findViewById(id.targetText)
        targetTextView = findViewById(id.targetTextView1)
        editText = findViewById(id.editText)
        new = findViewById(R.id.pScore)
        new2 = findViewById(R.id.aiScore)

        setTarget()
        rollDice()
        eachRoundWins()

        playerTotalScore.text = "H: ${playerTotalWins.size}"
        computerTotalScore.text = "C: ${computerTotalWins.size}"

//        set an onClickListener to re roll button
        reRollButton.setOnClickListener {
//          enable the score button
            scoreButton.isEnabled = true
//          check the if game is started or not
            if (dicesValuePlayer.size == 0){
                Toast.makeText(this,"Please Throw first time",Toast.LENGTH_SHORT).show()
            }else{
//              if started calling the getReRolledDices() function
                getReRolledDices()
            }
        }

//      set setOnClickListeners to make dice images clickable and add the clicked dices to an Array
        playerDiceImage1.setOnClickListener {
            Toast.makeText(this,"Dice 1 Selected",Toast.LENGTH_SHORT).show()

            playerDiceImage1.setBackgroundResource(drawable.background_lines)
//          if selected dice is not in the array it will add
            if(!clickedImages.contains(1)){
//                clickedImages.add(1)
                clickedImages.add(2)
                clickedImages.add(3)
                clickedImages.add(4)
                clickedImages.add(5)

            }else{
//              else selected dice is already in the array it will remove
                Toast.makeText(this,"Dice 1 Unselected",Toast.LENGTH_SHORT).show()
                playerDiceImage1.setBackgroundColor(Color.TRANSPARENT)
                clickedImages.remove(1)
                clickedImages.remove(2)
                clickedImages.remove(3)
                clickedImages.remove(4)
                clickedImages.remove(5)
            }
        }

        playerDiceImage2.setOnClickListener {
            Toast.makeText(this,"Dice 2 Selected",Toast.LENGTH_SHORT).show()
            playerDiceImage2.setBackgroundResource(drawable.background_lines)
            if(!clickedImages.contains(2)) {
//                clickedImages.add(2)
                clickedImages.add(1)
                clickedImages.add(3)
                clickedImages.add(4)
                clickedImages.add(5)
            }else{
                Toast.makeText(this,"Dice 2 Unselected",Toast.LENGTH_SHORT).show()
                playerDiceImage2.setBackgroundColor(Color.TRANSPARENT)
                clickedImages.remove(2)
                clickedImages.remove(1)
                clickedImages.remove(3)
                clickedImages.remove(4)
                clickedImages.remove(5)
            }
        }

        playerDiceImage3.setOnClickListener {
            Toast.makeText(this,"Dice 3 Selected",Toast.LENGTH_SHORT).show()
            playerDiceImage3.setBackgroundResource(drawable.background_lines)
            if(!clickedImages.contains(3)){
                clickedImages.add(3)
                clickedImages.add(1)
                clickedImages.add(2)
                clickedImages.add(4)
                clickedImages.add(5)
            }else{
                Toast.makeText(this,"Dice 3 Unselected",Toast.LENGTH_SHORT).show()
                playerDiceImage3.setBackgroundColor(Color.TRANSPARENT)
                clickedImages.remove(3)
                clickedImages.remove(1)
                clickedImages.remove(2)
                clickedImages.remove(4)
                clickedImages.remove(5)
            }
        }

        playerDiceImage4.setOnClickListener {
            Toast.makeText(this,"Dice 4 Selected",Toast.LENGTH_SHORT).show()
            playerDiceImage4.setBackgroundResource(drawable.background_lines)

            if(!clickedImages.contains(4)){
//                clickedImages.add(4)
                clickedImages.add(1)
                clickedImages.add(2)
                clickedImages.add(3)
                clickedImages.add(5)
            }else{
                Toast.makeText(this,"Dice 4 Unselected",Toast.LENGTH_SHORT).show()
                playerDiceImage4.setBackgroundColor(Color.TRANSPARENT)
                clickedImages.remove(4)
                clickedImages.remove(1)
                clickedImages.remove(2)
                clickedImages.remove(3)
                clickedImages.remove(5)
            }
        }

        playerDiceImage5.setOnClickListener {
            Toast.makeText(this,"Dice 5 Selected",Toast.LENGTH_SHORT).show()
            playerDiceImage5.setBackgroundResource(drawable.background_lines)

            if(!clickedImages.contains(5)){
//                clickedImages.add(5)
                clickedImages.add(1)
                clickedImages.add(2)
                clickedImages.add(3)
                clickedImages.add(4)
            }else{
                playerDiceImage5.setBackgroundColor(Color.TRANSPARENT)
                Toast.makeText(this,"Dice 5 Unselected",Toast.LENGTH_SHORT).show()
                clickedImages.remove(5)
                clickedImages.remove(1)
                clickedImages.remove(2)
                clickedImages.remove(3)
                clickedImages.remove(4)
            }
        }
    }

//  when player clicks the back button application allows to restart
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

//  function to generate and return a random number 1 to 6
    private fun genRandom():Int{
        return Random.nextInt(1,7)
    }

//  function to return an Integer array with generated numbers by calling genRandom() function
    private fun getDicesValues():IntArray{
        return intArrayOf(
            genRandom(),
            genRandom(),
            genRandom(),
            genRandom(),
            genRandom(),
        )
    }

//  a function to roll dices process
    private fun rollDice() {
//      clear selected dices the array
        dicesValuePlayer.clear()
//      check the human or computer already reached the target by calling the gameOverResult() function
         gameOverResult()
//      set th reRollBtn to clickable and scoreButton to unclickable
        reRollButton.isEnabled = true
        scoreButton.isEnabled = false


//      set setOnClickListener to throw (rollButton) button for rolling dices process
        rollButton.setOnClickListener {
            playerDiceImage1.setBackgroundColor(Color.TRANSPARENT)
            playerDiceImage2.setBackgroundColor(Color.TRANSPARENT)
            playerDiceImage3.setBackgroundColor(Color.TRANSPARENT)
            playerDiceImage4.setBackgroundColor(Color.TRANSPARENT)
            playerDiceImage5.setBackgroundColor(Color.TRANSPARENT)

            eachRoundWins()
//          clear selected dices the array
            clickedImages.clear()
            dicesValuePlayer.clear()
            playerCurrentRollTotal=0
            computerCurrentRollTotal = 0

//          when player started the game by clicking the throw button can't change the winning target
            setButton.isEnabled =false

//          calling for the randomGenerate function to generate 10 random dices according to the random number
            randomGenerate(
                showPlayerImage1 = playerResultImage1,
                showPlayerImage2 = playerResultImage2,
                showPlayerImage3 = playerResultImage3,
                showPlayerImage4 = playerResultImage4,
                showPlayerImage5 = playerResultImage5,
                showComputerImage1 = computerResultImage1,
                showComputerImage2 = computerResultImage2,
                showComputerImage3 = computerResultImage3,
                showComputerImage4 = computerResultImage4,
                showComputerImage5 = computerResultImage5
            )
//          set the total scores of human and the computer as strings in variables
            totalScoreText1 = score1.toString()
            totalScoreText2 = score2.toString()

//          increment the count according to the rounds already rolled
            count++
            if (count == 5) {
//
                "Total Score\n$totalScoreText1".also { rollText.text = it }
                "Total Score\n$totalScoreText2".also { rollText2.text = it }
//              check the human score or computer score is greater than or equal to the target score by calling the setTarget function
                if (score1 >= setTarget() || score2 >= setTarget()){
                    rollButton.isEnabled = false
                    gameOverResult()
                }else{
//                  if it's not game will allows user to throw again by setting count as 4 and enabling the both reRollBtn and rollButton
                    reRollButton.isEnabled = true
                    rollButton.isEnabled = true
                    count = 4
                }
            }

//          when player clicks the throw button score will hide by using an empty string
            rollText.text = ""
            rollText2.text = ""

//          set setOnClickListener to score button
            scoreButton.setOnClickListener {
                "Total Score\n$totalScoreText1".also { rollText.text = it }
                "Total Score\n$totalScoreText2".also { rollText2.text = it }
//              check whether game is over or not by calling the gameOverResult function
                gameOverResult()
//              clear the selected dice array
                dicesValuePlayer.clear()

//              when user clicked the score button it will set as unclickable until player throws dices again
                scoreButton.isEnabled = false
            }

        }
    }

//  function to calculate and return the sum of an Integer array for calculate the total of dices
    private fun calculateSum(value: IntArray): Int{
        total = value.sum()
        return total
    }

//  function to calculate the the total wins of human
    private fun calculateHumanGameResults(): Int{
        val human = IntArray(10)
        for (i in human.indices) {
            human[i] = i + 1
        }
        humanTotalWins = human.sum()
        playerTotalScore.text = humanTotalWins.toString()
        return humanTotalWins
    }

//  function to generate random dice images according to the random generated integers
    private fun randomGenerate(
        showPlayerImage1: ImageView,
        showPlayerImage2: ImageView,
        showPlayerImage3: ImageView,
        showPlayerImage4: ImageView,
        showPlayerImage5: ImageView,
        showComputerImage1: ImageView,
        showComputerImage2: ImageView,
        showComputerImage3: ImageView,
        showComputerImage4: ImageView,
        showComputerImage5: ImageView,
    ) {
        gameOverResult()

//      clear the selected dices array
        dicesValuePlayer.clear()

//      set deicesValuePlayer as a MUTABLE LIST
        dicesValuePlayer = getDicesValues().toMutableList()
//      set the integers as variables according to the index of array
        val playerRandomInt1 = dicesValuePlayer[0]
        val playerRandomInt2 = dicesValuePlayer[1]
        val playerRandomInt3 = dicesValuePlayer[2]
        val playerRandomInt4 = dicesValuePlayer[3]
        val playerRandomInt5 = dicesValuePlayer[4]

//      set the player's total by calling the calculateSum function
        playerSum = calculateSum(dicesValuePlayer.toIntArray())

//      set total of five dices in each roll
//        val new = findViewById<TextView>(R.id.pScore)
        new.text = playerSum.toString()

//      set setOnClickListener to score button to display the current total score, clear the array and increment the current player's score
        scoreButton.setOnClickListener {
            dicesValuePlayer.clear()
//            val new = findViewById<TextView>(R.id.pScore)
            new.text = total.toString()
            new.text = playerSum.toString()
            score1 += playerSum


            "Total Score\n$score1".also { throwText.text = it }
            "Total Score\n$score2".also { throwText2.text = it }
            scoreButton.isEnabled = false
//            throwText.text = score1.toString()
//            throwText2.text = score2.toString()
        }
        score1 += playerSum
        "Total Score\n$totalScoreText1".also { rollText.text = it }
        "Total Score\n$totalScoreText2".also { rollText2.text = it }



//      set dicesValueComputer as a MUTABLE LIST
        dicesValueComputer = getDicesValues().toMutableList()
//      set the integers as variables according to the index of array (computer)
        val computerRandomInt1 = dicesValueComputer[0]
        val computerRandomInt2 = dicesValueComputer[1]
        val computerRandomInt3 = dicesValueComputer[2]
        val computerRandomInt4 = dicesValueComputer[3]
        val computerRandomInt5 = dicesValueComputer[4]

//      calculate the som of array by calling the calculateSum function
        computerSum = calculateSum(dicesValueComputer.toIntArray())
//        val new2 = findViewById<TextView>(R.id.aiScore)
        new2.text = computerSum.toString()
//      increment the total score of computer
        score2 += computerSum


//      update the image player by calling updateImage function and store as an integer according to the generated random integers
        val playerDrawableImage1 = updateImage(playerRandomInt1)
        val playerDrawableImage2 = updateImage(playerRandomInt2)
        val playerDrawableImage3 = updateImage(playerRandomInt3)
        val playerDrawableImage4 = updateImage(playerRandomInt4)
        val playerDrawableImage5 = updateImage(playerRandomInt5)

//      update the image computer by calling updateImage function and store as an integer according to the generated random integers
        val computerDrawableImage1 = updateImage(computerRandomInt1)
        val computerDrawableImage2 = updateImage(computerRandomInt2)
        val computerDrawableImage3 = updateImage(computerRandomInt3)
        val computerDrawableImage4 = updateImage(computerRandomInt4)
        val computerDrawableImage5 = updateImage(computerRandomInt5)

//      set the image views of player according to the above integers
        showPlayerImage1.setImageResource(playerDrawableImage1)
        showPlayerImage2.setImageResource(playerDrawableImage2)
        showPlayerImage3.setImageResource(playerDrawableImage3)
        showPlayerImage4.setImageResource(playerDrawableImage4)
        showPlayerImage5.setImageResource(playerDrawableImage5)

//      set the image views of computer according to the above integers
        showComputerImage1.setImageResource(computerDrawableImage1)
        showComputerImage2.setImageResource(computerDrawableImage2)
        showComputerImage3.setImageResource(computerDrawableImage3)
        showComputerImage4.setImageResource(computerDrawableImage4)
        showComputerImage5.setImageResource(computerDrawableImage5)
    }

/*    ------------------- Documentation ---------------------
        When user (player) select the the dices to re roll computer player also selecting the same
        amount of dices but from different positions as below implementation of function it's works
        like this,
            if player select the dice 1 ---> computer will select dice 5
            if player select the dice 2 ---> computer will select dice 4
            if player select the dice 3 ---> computer will select dice 1
            if player select the dice 4 ---> computer will select dice 3
            if player select the dice 5 ---> computer will select dice 2

        user (player) allows 2 re rolls. If user wants user can add total of re roll in first time
        or if user not satisfied with that amount user can re roll again but now user can only use
        that score by clicking score button because re roll button is now disabled. After that the
        total of each roll will adding to the overall total scores (player total score & computer total score)

        So I think this is fair for both human and computer players because we don't know what is
        the actual roll value until it's rolled.
    ------------------------------------------------------------*/

//   function to get the re rolled dices
    private fun getReRolledDices(){

        Toast.makeText(this,"${clickedImages.size} Dices Selected", Toast.LENGTH_SHORT).show()
//      get the selected dices using a for loop before click re roll button
        for(i in clickedImages.indices){
//          check the clicked dices and update the dice images according to the random integer which is generated and update the score
            if(clickedImages[i] == 1){
                val playerRandomInt1 = (1..6).random()
                val playerDrawableImage1 = updateImage(playerRandomInt1)
                dicesValuePlayer.set(0, playerRandomInt1)
                calculateSum(dicesValuePlayer.toIntArray())
                val showPlayerImage1 = findViewById<ImageView>(R.id.playerDiceImage1)
                showPlayerImage1.setImageResource(playerDrawableImage1)
                score1 += playerRandomInt1

                dicesValueComputer.set(4, computerRandomInt5)
                calculateSum(dicesValueComputer.toIntArray())
                val showComputerImage5 = findViewById<ImageView>(R.id.computerDiceImage5)
                showComputerImage5.setImageResource(computerDrawableImage5)
                score2 += computerRandomInt5
            }
            if(clickedImages[i] == 2){
                val playerRandomInt2 = (1..6).random()
                val playerDrawableImage2 = updateImage(playerRandomInt2)
                dicesValuePlayer.set(1, playerRandomInt2)
                calculateSum(dicesValuePlayer.toIntArray())
                val showPlayerImage2 = findViewById<ImageView>(R.id.playerDiceImage2)
                showPlayerImage2.setImageResource(playerDrawableImage2)
                score1 += playerRandomInt2

                dicesValueComputer.set(3, computerRandomInt4)
                calculateSum(dicesValueComputer.toIntArray())
                val showComputerImage4 = findViewById<ImageView>(R.id.computerDiceImage4)
                showComputerImage4.setImageResource(computerDrawableImage4)
                score2 += computerRandomInt4

            }
            if(clickedImages[i] == 3){
                val playerRandomInt3 = (1..6).random()
                val playerDrawableImage3 = updateImage(playerRandomInt3)
                dicesValuePlayer.set(2, playerRandomInt3)
                calculateSum(dicesValuePlayer.toIntArray())
                val showPlayerImage3 = findViewById<ImageView>(R.id.playerDiceImage3)
                showPlayerImage3.setImageResource(playerDrawableImage3)
                score1 += playerRandomInt3

                dicesValueComputer.set(0, computerRandomInt1)
                calculateSum(dicesValueComputer.toIntArray())
                val showComputerImage1 = findViewById<ImageView>(R.id.computerDiceImage1)
                showComputerImage1.setImageResource(computerDrawableImage1)
                score2 += computerRandomInt1
            }
            if(clickedImages[i] == 4){
                val playerRandomInt4 = (1..6).random()
                val playerDrawableImage4 = updateImage(playerRandomInt4)
                dicesValuePlayer.set(3, playerRandomInt4)
                calculateSum(dicesValuePlayer.toIntArray())
                val showPlayerImage4 = findViewById<ImageView>(R.id.playerDiceImage4)
                showPlayerImage4.setImageResource(playerDrawableImage4)
                score1 += playerRandomInt4

                dicesValueComputer.set(2, computerRandomInt3)
                calculateSum(dicesValueComputer.toIntArray())
                val showComputerImage3 = findViewById<ImageView>(R.id.computerDiceImage3)
                showComputerImage3.setImageResource(computerDrawableImage3)
                score2 += computerRandomInt3
            }
            if(clickedImages[i] == 5){
                val playerRandomInt5 = (1..6).random()
                val playerDrawableImage5 = updateImage(playerRandomInt5)
                dicesValuePlayer.set(4, playerRandomInt5)
                calculateSum(dicesValuePlayer.toIntArray())
                val showPlayerImage5 = findViewById<ImageView>(R.id.playerDiceImage5)
                showPlayerImage5.setImageResource(playerDrawableImage5)
                score1 += playerRandomInt5

                dicesValueComputer.set(1, computerRandomInt2)
                calculateSum(dicesValueComputer.toIntArray())
                val showComputerImage2 = findViewById<ImageView>(R.id.computerDiceImage2)
                showComputerImage2.setImageResource(computerDrawableImage2)
                score2 += computerRandomInt2
            }
        }
    }

//  function to update the image and return the integer of that image according to the random integers
    private fun updateImage(computerRandomInt:Int):Int{
       val computerDrawableImage = when(computerRandomInt){
            1 -> drawable.dice_1
            2 -> drawable.dice_2
            3 -> drawable.dice_3
            4 -> drawable.dice_4
            5 -> drawable.dice_5
            else -> drawable.dice_6
        }
        return computerDrawableImage
    }


    private fun eachRoundWins(){
        if( playerSum  > computerSum) {
            playerTotalWins.add(1)
        }else if (computerSum > playerSum){
            computerTotalWins.add(1)
        }
    }

//  function to run when score of player or computer reached the target
    private fun gameOverResult(){
        if (score1 >= setTarget()){
            scoreButton.isEnabled = true
            if (score1 < score2){
                computerTotalWins.add(1)
                Toast.makeText(this,"ctw is ${computerTotalWins.size}",Toast.LENGTH_SHORT).show()

//                calculateComputerGameResults()
//              calling for popup function with different font color (RED) and result text (YOU LOSE)
                popup("You Lose", "#F55050")
//              disable the re roll button and enable the score button
                reRollButton.isEnabled = false
                scoreButton.isEnabled = true
            }
            else if(score2 < score1){
                playerTotalWins.add(1)
                Toast.makeText(this,"ptw is ${playerTotalWins.size}",Toast.LENGTH_SHORT).show()

//                calculateHumanGameResults()
//              calling for popup function with different font color (GREEN) and result text (YOU WON)
                popup("You Won!", "#A1DD70")
//              disable the re roll button and enable the score button
                reRollButton.isEnabled = false
                scoreButton.isEnabled = true
            }
        }else if (score2 >= value){
            computerTotalWins.add(1)
            Toast.makeText(this,"ctw is ${computerTotalWins.size}",Toast.LENGTH_SHORT).show()

            scoreButton.isEnabled = true
//            calculateComputerGameResults()
            popup("You Lose", "#F55050")
            reRollButton.isEnabled = false
        }
        playerTotalScore.text = "H: ${playerTotalWins.size}"
        computerTotalScore.text = "C: ${computerTotalWins.size}"
    }

//  function to popup a window when game is over
    private fun popup(message: String, color: String){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.score_popup, null)
        val popupWindow = PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
//      set the popup window location to the center
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
//      set background color
        parentLayout.alpha = 0.9F

//      text color according to the result (win/lose)
        view.gameResult.setTextColor(Color.parseColor(color))

        view.gameResult.text = message

//      disable roll and re roll buttons
        rollButton.isEnabled = false
        reRollButton.isEnabled = false
//      when click outside of popup window it will close
        popupWindow.isOutsideTouchable = true
    }

//  function to set the winning target of game and return that target value
    private fun setTarget() :Int{
        setButton.setOnClickListener {
             val targetValue: String = editText.text.toString()
//          set value to an integer
            value = targetValue.toInt()
            "Target\n$targetValue".also { targetTextView.text = it }
//          after setting the value button is unclickable
            setButton.isEnabled = false
            targetTextView.isEnabled = false
//          focus the text input view when keyboard was displayed
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.inputType = TYPE_NULL
            val view: View? = this.currentFocus

//          on below line checking if view is not null.
            if (view != null) {
//              on below line we are creating a variable
//              for input manager and initializing it.
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

//              on below line hiding our keyboard.
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        return value
    }

}

