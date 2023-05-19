package com.kaibltdinc.melancholian

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.ViewGroup
import android.widget.ImageView
import com.kaibltdinc.melancholian.databinding.ActivityGameBinding

class Game : AppCompatActivity() {
    private val slots = arrayOfNulls<ImageView>(9)
    private val slotImages = arrayOf(
        R.drawable.slotik1,
        R.drawable.slotik2,
        R.drawable.slotik3,
        R.drawable.slotik4,
        R.drawable.slotik5,
        R.drawable.slotik6
    )
    private val slotValues = IntArray(9)
    private val handler = Handler()
    private var score = 2000
    private var stavb = 200
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        slots[0] = findViewById(R.id.slot1)
        slots[1] = findViewById(R.id.slot2)
        slots[2] = findViewById(R.id.slot3)
        slots[3] = findViewById(R.id.slot4)
        slots[4] = findViewById(R.id.slot5)
        slots[5] = findViewById(R.id.slot6)
        slots[6] = findViewById(R.id.slot7)
        slots[7] = findViewById(R.id.slot8)
        slots[8] = findViewById(R.id.slot9)
        binding.pointer.text = score.toString()
        binding.pluse.setOnClickListener {
            val params: ViewGroup.LayoutParams =
                binding.pluse.layoutParams as ViewGroup.LayoutParams
            params.width -= 3
            params.height -= 3
            binding.pluse.layoutParams = params
            handler.postDelayed({
                params.width += 3
                params.height += 3
                binding.pluse.layoutParams = params
            }, 120)
            var plusBet: Int = 0
            if (binding.editStavb.text.toString() == "") {
                plusBet += 200
                binding.editStavb.text =
                    Editable.Factory.getInstance().newEditable(plusBet.toString())
            } else {

                plusBet = binding.editStavb.text.toString().toInt()
                plusBet += 200
                binding.editStavb.text =
                    Editable.Factory.getInstance().newEditable(plusBet.toString())
            }
        }
        //minusBet
        binding.minusus.setOnClickListener {
            val params: ViewGroup.LayoutParams =
                binding.minusus.layoutParams as ViewGroup.LayoutParams
            params.width -= 3
            params.height -= 3
            binding.minusus.layoutParams = params
            handler.postDelayed({
                params.width += 3
                params.height += 3
                binding.minusus.layoutParams = params
            }, 120)
            var minusBet: Int
            if (binding.editStavb.text.toString() == "200" || binding.editStavb.text.toString() == "0" || binding.editStavb.text.toString() == "100") {
                minusBet = binding.editStavb.text.toString().toInt()
                minusBet -= 200
                binding.editStavb.text =
                    Editable.Factory.getInstance().newEditable(minusBet.toString())
            }
        }
        binding.beginPl.setOnClickListener {
            val params: ViewGroup.LayoutParams =
                binding.beginPl.layoutParams as ViewGroup.LayoutParams
            params.width -= 3
            params.height -= 3
            binding.beginPl.layoutParams = params
            handler.postDelayed({
                params.width += 3
                params.height += 3
                binding.beginPl.layoutParams = params
            }, 120)
            stavb = binding.editStavb.text.toString().toInt()
            if (stavb <= score && score >= 0) {
                score -= stavb
                binding.pointer.text = score.toString()
            }
            beginPlay()
        }

    }

    private fun beginPlay() {
        var animationCount = 0

        for (i in slots.indices) {
            animateSlotRotation(slots[i], i) {
                animationCount++
                if (animationCount == slots.size) {
                    checkMatches()
                }
            }
        }
    }


    private fun animateSlotRotation(slot: ImageView?, index: Int, onAnimationEnd: () -> Unit) {
        val rotation = ObjectAnimator.ofFloat(slot, "rotation", 0f, 360f)
        rotation.duration = 1000
        rotation.repeatCount = 3
        rotation.addUpdateListener { animator ->
            val currentRotation = animator.currentPlayTime % rotation.duration
            if (currentRotation >= rotation.duration / 2) {
                val slotValue = slotImages.random()
                slotValues[index] = slotValue
            }
        }
        rotation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                slot?.setImageResource(slotValues[index])
                onAnimationEnd()
            }
        })
        rotation.start()
    }

    private fun checkMatches() {
        for (row in 0 until 3) {
            val startIndex = row * 3
            val value = slotValues[startIndex]
            var matchCount = 1

            for (i in startIndex + 1 until startIndex + 3) {
                if (slotValues[i] == value) {
                    matchCount++
                }
            }

            if (matchCount == 2) {
                stavb *= 2
                score += stavb
            } else if (matchCount == 3) {
                stavb *= 3
                score += stavb
            }
        }
        binding.pointer.text = score.toString()
    }
}