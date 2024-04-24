package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen(navController = navController)
            }
            composable("modeSelection") {
                ModeSelectionScreen(navController = navController)
            }
            composable("driver") {
                DriverModeScreen(navController = navController)
            }
            composable("pedestrian") {
                PedestrianModeScreen(navController = navController)
            }
            // 추가 화면 경로 설정...
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val startAnimation = remember { mutableStateOf(true) }
    LaunchedEffect(key1 = true) {
        if (startAnimation.value) {
            delay(2000)
            navController.navigate("modeSelection")
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "스몸비파인더", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun ModeSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp), // 전체 Column에 적용되는 패딩
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button의 Modifier를 사용하여 버튼의 크기를 조정합니다.
        // 여기서는 minHeight를 사용하여 버튼의 최소 높이를 설정합니다.
        Button(
            onClick = { navController.navigate("driver") },
            modifier = Modifier.height(200.dp) // 버튼의 높이를 56.dp로 설정
        ) {
            // Text의 style을 통해 글자 크기를 조정합니다.
            Text("운전자모드", style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(modifier = Modifier.height(60.dp))
        // 두 번째 버튼도 동일하게 적용합니다.
        Button(
            onClick = { navController.navigate("pedestrian") },
            modifier = Modifier.height(200.dp) // 버튼의 높이를 56.dp로 설정
        ) {
            Text("보행자모드", style = MaterialTheme.typography.headlineSmall)
        }
    }
}

// 운전자 모드 화면
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverModeScreen(navController: NavController) {
    val items = listOf("홈", "지도", "설정")
    var selectedItem by remember { mutableStateOf(0) }

    // 배경색을 탭 선택에 따라 변경
    val backgroundColors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary)
    val backgroundColor = backgroundColors[selectedItem]

    Scaffold(
        topBar = { TopAppBar(title = { Text("운전자 모드") }) },
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        }
                    )
                }
            }
        },
        containerColor = backgroundColor // 배경색 설정
    ) {
        // 화면 컨텐츠를 여기에 추가...
        Box(modifier = Modifier.padding(it)) {
            when (selectedItem) {
                0 -> Text("홈 화면")
                1 -> Text("지도 화면")
                2 -> Text("설정 화면")
            }
        }
    }
}

// 보행자 모드 화면
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedestrianModeScreen(navController: NavController) {
    // "보행자 모드"일 때 사용할 탭 목록을 정의합니다.
    val items = listOf("홈", "설정")
    var selectedItem by remember { mutableStateOf(0) }

    // 배경색을 탭 선택에 따라 변경
    val backgroundColors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    val backgroundColor = backgroundColors[selectedItem]

    Scaffold(
        topBar = { TopAppBar(title = { Text("보행자 모드") }) },
        bottomBar = {
            BottomNavigation {
                items.forEachIndexed { index, item ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        }
                    )
                }
            }
        },
        containerColor = backgroundColor // 배경색 설정
    ) {
        // 화면 컨텐츠를 여기에 추가...
        Box(modifier = Modifier.padding(it)) {
            when (selectedItem) {
                0 -> Text("홈 화면")
                // "지도 화면" 탭을 제거했습니다.
                1 -> Text("설정 화면")
            }
        }
    }
}
