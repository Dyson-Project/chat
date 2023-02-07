package org.dyson.chat.exceptions


class WSException(code: Int, description: String? = null) : RuntimeException(description) {
}
