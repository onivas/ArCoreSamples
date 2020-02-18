package hu.stewe93.arcoresamples.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import hu.stewe93.arcoresamples.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel = MainViewModel()

    private var model: ModelRenderable? = null
    private lateinit var arFragment: ArFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arFragment = childFragmentManager.findFragmentById(R.id.arView) as ArFragment

        loadModel()

        bigger.setOnClickListener {
            viewModel.increase()
        }
        smaller.setOnClickListener {
            viewModel.decrease()
        }
        left.setOnClickListener {
            viewModel.moveLeft()
        }
        right.setOnClickListener {
            viewModel.moveRight()
        }
        front.setOnClickListener {
            viewModel.moveFront()
        }
        back.setOnClickListener {
            viewModel.moveBack()
        }
        turnLeft.setOnClickListener {
            viewModel.turnLeft()
        }
        turnRight.setOnClickListener {
            viewModel.turnRight()
        }
        delete.setOnClickListener {
            viewModel.delete()
        }
    }

    private fun initTapListener() {
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchorNode = AnchorNode(
                hitResult.createAnchor()
            )

            anchorNode.setParent(arFragment.arSceneView.scene)
            val node = Node()
            node.setOnTapListener { hitTestResult, motionEvent ->
                viewModel.setSelectedNode(hitTestResult.node, motionEvent)
            }
            node.localScale = Vector3(0.2.toFloat(), 0.2.toFloat(), 0.2.toFloat())
            node.renderable = model
            node.setParent(anchorNode)
        }
    }

    private fun loadModel() {
        lifecycleScope.launch {
            model = ModelRenderable
                .builder()
                .setSource(
                    context,
                    Uri.parse("scene.sfb")
                )
                .build()
                .await()
            Toast.makeText(
                requireContext(),
                "Model available",
                Toast.LENGTH_SHORT
            ).show()
            initTapListener()
        }
    }

}