package hu.stewe93.arcoresamples.ui.main

import android.view.MotionEvent
import androidx.lifecycle.ViewModel
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3


class MainViewModel : ViewModel() {

    private var selectedNode: Node? = null
    private var nodeMotionEvent: MotionEvent? = null

    private var increaseScale = 0.1.toFloat()
    private var angle = 10.toFloat()

    fun setSelectedNode(node: Node?, motionEvent: MotionEvent?) {
        reset()
        selectedNode = node
        nodeMotionEvent = motionEvent
    }

    fun increase() {
        selectedNode?.let {
            increaseScale += 0.1F
            it.localScale = Vector3(increaseScale, increaseScale, increaseScale)

        }
    }

    fun decrease() {
        selectedNode?.let {
            increaseScale -= 0.1F
            if (increaseScale >= 0.0) {
                it.localScale = Vector3(increaseScale, increaseScale, increaseScale)
            } else {
                increaseScale = 0.1F
            }
        }
    }

    private fun reset() {
        increaseScale = 0.2.toFloat()
    }

    fun moveLeft() {
        selectedNode?.let {
            val localPosition = it.localPosition
            it.localPosition = Vector3(
                localPosition.x - 0.1F,
                localPosition.y,
                localPosition.z
            )
        }
    }

    fun turnLeft() {
        selectedNode?.let {
            angle += 5f
            it.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), angle)
        }
    }

    fun turnRight() {
        selectedNode?.let {
            angle -= 5f
            it.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), angle)
        }
    }

    fun moveRight() {
        selectedNode?.let {
            val localPosition = it.localPosition
            it.localPosition = Vector3(
                localPosition.x + 0.1F,
                localPosition.y,
                localPosition.z
            )
        }
    }

    fun moveBack() {
        selectedNode?.let {
            val localPosition = it.localPosition
            it.localPosition = Vector3(
                localPosition.x,
                localPosition.y,
                localPosition.z - 0.1F
            )
        }
    }

    fun moveFront() {
        selectedNode?.let {
            val localPosition = it.localPosition
            it.localPosition = Vector3(
                localPosition.x,
                localPosition.y,
                localPosition.z + 0.1F
            )
        }
    }

    fun delete() {
        selectedNode?.isEnabled = false
    }
}