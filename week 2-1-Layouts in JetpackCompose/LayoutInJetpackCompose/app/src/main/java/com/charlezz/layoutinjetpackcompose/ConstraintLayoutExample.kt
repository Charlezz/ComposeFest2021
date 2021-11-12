package com.charlezz.layoutinjetpackcompose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import androidx.core.graphics.toColorLong
import com.charlezz.layoutinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme

//@Composable
//fun ConstraintLayoutContent() {
//    ConstraintLayout {
//
//        // 컴포저블을 통제하기 위해 참조들을 생성한다.
//        val (button, text) = createRefs()
//        Button(
//            onClick = { /* Do something */ },
//            // "button" 참조를 Button 컴포저블에 배정한다.
//            // 그리고 ConstraintLayout의 top에 제약조건을 설정한다.
//            modifier = Modifier.constrainAs(button) {
//                top.linkTo(parent.top, margin = 16.dp)
//                centerHorizontallyTo(parent)
//            }
//        ) {
//            Text("Button")
//        }
//
//
//        // "text" 참조를 Text 컴포저블에 배정한다.
//        // 그리고 Button 컴포저블 bottom에 제약조건을 설정한다.
//        Text("Text", Modifier.constrainAs(text) {
//            top.linkTo(button.bottom, margin = 16.dp)
//        })
//    }
//}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text("Button 2")
        }
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsInJetpackComposeTheme {
        ConstraintLayoutContent()
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.3f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
//                width = Dimension.wrapContent
                width = Dimension.preferredWrapContent.atLeast(200.dp)
//                width = Dimension.value(200.dp)
//                width = Dimension.preferredValue(200.dp).atLeast(200.dp)
            }
        )
    }
}


@Preview(widthDp = 200, heightDp = 200)
@Composable
fun LargeConstraintLayoutPreview() {
    LayoutsInJetpackComposeTheme {
        LargeConstraintLayout()
    }
}


@Composable
@Preview(widthDp = 200, heightDp = 199, showBackground = true, backgroundColor = 0xffffffff)
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraintSet = constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button","button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text","text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}
