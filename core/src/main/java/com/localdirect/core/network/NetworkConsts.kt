package com.localdirect.core.network

const val LOCALDIRECT_PORT = 3316

/**
 * Constants that only sending from client to server
 */
object ClientConst {
    const val LOCALDIRECT_HANDSHAKE = "com.localdirect.handshake"
    const val LOCALDIRECT_ESTABLISH = "com.localdirect.establish"

    const val LOCALDIRECT_MOUSE_LEFT = "com.localdirect.mouse.left"
}

/**
 * Constants that only sending from server to client
 */
object ServerConsts {
    const val LOCALDIRECT_ACCEPT = "com.localdirect.accept"
    const val LOCALDIRECT_TERMINAL = "com.localdirect.terminal"
}