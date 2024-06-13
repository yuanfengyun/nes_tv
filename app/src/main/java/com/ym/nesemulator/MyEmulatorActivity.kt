package com.ym.nesemulator

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.hardware.input.InputManager
import android.os.Bundle
import android.view.InputDevice
import android.view.InputEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import android.widget.Toast
import com.ym.library.base.BaseEmulatorActivity
import com.ym.library.sdk.EmulatorManager

/**
 * @Desc:自定义Nes游戏界面 在这里可以定义界面控件 诸如按钮等
 * @Author:Kevin
 * @Date:2023/2/17 15:25:37
 */
class MyEmulatorActivity : BaseEmulatorActivity(), OnTouchListener{
    private var oldAxisX = 0f
    private var oldAxisY = 0f
    private var oldAxisZ = 0f
    private var oldAxisRZ = 0f
    private var oldAxisLTRIGGER = 0f
    private var oldAxisRTRIGGER = 0f
    private var player1 = -1;
    private var player2 = -1;
    //组件map 组件id为key value储存游戏玩家和对应的控制key
    private val keyCompMap: HashMap<Int, Pair<EmulatorManager.Player, ArrayList<EmulatorManager.ControllerKey>>> =
        HashMap()


    override fun getLayoutResId(): Int {
        return R.layout.activity_nes_emulator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        this.initKeyCompMap()
    }

    /**
     * 初始化控件-key的映射
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun initKeyCompMap() {
        //Player 1
        findViewById<TextView>(R.id.keyLeft)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.LEFT)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyUp)?.apply {
            keyCompMap[id] =
                Pair(EmulatorManager.Player.PLAYER1, arrayListOf(EmulatorManager.ControllerKey.UP))
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyRight)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.RIGHT)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyDown)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.DOWN)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyLeftUp)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.LEFT, EmulatorManager.ControllerKey.UP)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyRightUp)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.RIGHT, EmulatorManager.ControllerKey.UP)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyRightDown)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.RIGHT, EmulatorManager.ControllerKey.DOWN)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyLeftDown)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.LEFT, EmulatorManager.ControllerKey.DOWN)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keySelect)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.SELECT)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyStart)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.START)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyA)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.A)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyB)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.B)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyATurbo)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.A_TURBO)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyBTurbo)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER1,
                arrayListOf(EmulatorManager.ControllerKey.B_TURBO)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }

        //Player 2
        findViewById<TextView>(R.id.keyLeft2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.LEFT)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyUp2)?.apply {
            keyCompMap[id] =
                Pair(EmulatorManager.Player.PLAYER2, arrayListOf(EmulatorManager.ControllerKey.UP))
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyRight2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.RIGHT)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyDown2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.DOWN)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyLeftUp2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.LEFT, EmulatorManager.ControllerKey.UP)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyRightUp2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.RIGHT, EmulatorManager.ControllerKey.UP)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyRightDown2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.RIGHT, EmulatorManager.ControllerKey.DOWN)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyLeftDown2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.LEFT, EmulatorManager.ControllerKey.DOWN)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keySelect2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.SELECT)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyStart2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.START)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyA2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.A)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyB2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.B)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyATurbo2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.A_TURBO)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
        findViewById<TextView>(R.id.keyBTurbo2)?.apply {
            keyCompMap[id] = Pair(
                EmulatorManager.Player.PLAYER2,
                arrayListOf(EmulatorManager.ControllerKey.B_TURBO)
            )
            this.setOnTouchListener(this@MyEmulatorActivity)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {//按下
                val player = this.keyCompMap[v?.id]?.first
                this.keyCompMap[v?.id]?.second?.forEach {
                    EmulatorManager.getInstance()
                        .pressKey(player, it)
                }
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {//松开
                val player = this.keyCompMap[v?.id]?.first
                this.keyCompMap[v?.id]?.second?.forEach {
                    EmulatorManager.getInstance()
                        .unPressKey(player, it)
                }
            }
        }
        return true
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onGenericMotionEvent(event: MotionEvent): Boolean {
        // 检查是否是手柄设备
        if (isJoyStick(event)) {
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    // 获取左摇杆的轴数据
                    val axisX = event.getAxisValue(MotionEvent.AXIS_X)
                    val axisY = event.getAxisValue(MotionEvent.AXIS_Y)
                    if (axisX != oldAxisX || axisY != oldAxisY) {
                        // 处理左摇杆移动
                        handleJoystickMovement(event.deviceId,axisX, axisY)
                        oldAxisX = axisX
                        oldAxisY = axisY
                    }

                    // 获取右摇杆的轴数据
                    val axisZ = event.getAxisValue(MotionEvent.AXIS_Z)
                    val axisRZ = event.getAxisValue(MotionEvent.AXIS_RZ)
                    if (axisZ != oldAxisZ || axisRZ != oldAxisRZ) {
                        // 处理右摇杆移动
                        handleJoystickMovement(event.deviceId,axisZ, axisRZ)
                        oldAxisZ = axisZ
                        oldAxisRZ = axisRZ
                    }

                    // 获取扳机键的轴数据
                    val axisLTRIGGER = event.getAxisValue(MotionEvent.AXIS_LTRIGGER)
                    val axisRTRIGGER = event.getAxisValue(MotionEvent.AXIS_RTRIGGER)
                    if (axisLTRIGGER != oldAxisLTRIGGER || axisRTRIGGER != oldAxisRTRIGGER) {
                        // 处理扳机键
                        handleTriggers(axisLTRIGGER, axisRTRIGGER)
                        oldAxisLTRIGGER = axisLTRIGGER
                        oldAxisRTRIGGER = axisRTRIGGER
                    }
                }
            }
        }
        return super.onGenericMotionEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        // 检查是否是手柄设备
        if (isJoyStick(event)) {
            var p=EmulatorManager.Player.PLAYER1;
            if(player1==-1){

                player1 = event.deviceId
            }else if(player1!=event.deviceId){
                player2 = event.deviceId
                p = EmulatorManager.Player.PLAYER2
            }
            //Toast.makeText(this, "Button Pressed"+event.keyCode.toString(), Toast.LENGTH_SHORT).show()
            var down = (event.action == KeyEvent.ACTION_DOWN)
            var up = (event.action == KeyEvent.ACTION_UP)
            when (event.keyCode) {
                KeyEvent.KEYCODE_BUTTON_SELECT ->{
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.SELECT)
                    else if (up)
                    EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.SELECT)
                    return true
                }
                KeyEvent.KEYCODE_BUTTON_START ->{
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.START)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.START)
                    return true
                }
                KeyEvent.KEYCODE_BUTTON_A -> {
                    // 处理A键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.A)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.A)
                    return true
                }
                KeyEvent.KEYCODE_BUTTON_B -> {
                    // 处理B键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.B)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.B)
                    return true
                }
                KeyEvent.KEYCODE_BUTTON_X -> {
                    // 处理B键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.A_TURBO)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.A_TURBO)
                    return true
                }
                KeyEvent.KEYCODE_BUTTON_Y -> {
                    // 处理B键按下
                    // 处理B键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.B_TURBO)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.B_TURBO)
                    return true
                }
                19 -> {
                    // 处理B键按下
                    // 处理B键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.UP)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.UP)
                    return true
                }
                20 -> {
                    // 处理B键按下
                    // 处理B键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.DOWN)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.DOWN)
                    return true
                }
                21 -> {
                    // 处理B键按下
                    // 处理B键按下
                    //Toast.makeText(this, "Button Pressed"+event.keyCode.toString(), Toast.LENGTH_SHORT).show()
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.LEFT)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.LEFT)
                    return true
                }
                22 -> {
                    // 处理B键按下
                    // 处理B键按下
                    if(down) EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.RIGHT)
                    else if (up)
                        EmulatorManager.getInstance().unPressKey(p, EmulatorManager.ControllerKey.RIGHT)
                    return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    private fun isJoyStick(event: InputEvent): Boolean {
        val sources = event.source
        return (sources and InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK ||
                (sources and InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD
    }

    private fun handleJoystickMovement(source: Int,x: Float, y: Float) {
        return
        // 根据x和y的值处理摇杆移动
        //Toast.makeText(this, "Joystick moved to ($x, $y)", Toast.LENGTH_SHORT).show()
        var p=EmulatorManager.Player.PLAYER1;
        if(player1!=source){
            p = EmulatorManager.Player.PLAYER2
        }
        if(x<0){
            EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.LEFT)
        }
        if(x>0){
            EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.RIGHT)
        }
        if(y>0){
            EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.UP)
        }
        if(y<0){
            EmulatorManager.getInstance().pressKey(p, EmulatorManager.ControllerKey.DOWN)
        }
    }

    private fun handleTriggers(leftTrigger: Float, rightTrigger: Float) {
        // 根据leftTrigger和rightTrigger的值处理扳机键
        //Toast.makeText(this, "Left Trigger: $leftTrigger, Right Trigger: $rightTrigger", Toast.LENGTH_SHORT).show()
    }
}