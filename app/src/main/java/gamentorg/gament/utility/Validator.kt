package gamentorg.gament.utility

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Validator @Inject constructor() {

    fun check(vararg validations: String) : String {

        for (err in validations) {
            if (err != "") {
                return err
            }
        }

        return ""
    }

    fun isEmpty(fieldName: String, text: String): String {
        return if (text == "") {
            "وارد کردن $fieldName الزامی است"
        } else {
            ""
        }
    }

    fun minLength(fieldName: String, text: String, length: Int): String {
        return if (text.length < length) {
            "$fieldName نمیتواند کمتر از $length عدد باشد"
        } else {
            ""
        }
    }

    fun maxLength(fieldName: String, text: String, length: Int): String {
        return if (text.length > length) {
            "$fieldName نمیتواند بیشتر از $length عدد باشد"
        } else {
            ""
        }
    }

    fun isFirstEquals(fieldName: String, text: String, inValidChar: String): String {
        return when {
            text == "" -> ""
            text.first() == inValidChar.single() -> "$fieldName خود را بدون $inValidChar ابتدایی وارد نمایید"
            else -> ""
        }
    }
}