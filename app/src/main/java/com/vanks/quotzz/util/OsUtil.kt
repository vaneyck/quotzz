package com.vanks.quotzz.util

import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager

fun getDeviceCountryCode(context: Context): String {
    var countryCode: String?

    // try to get country code from TelephonyManager service
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    if (tm != null) {
        // query first getSimCountryIso()
        countryCode = tm.simCountryIso
        if (countryCode != null && countryCode.length == 2)
            return countryCode.toLowerCase()

        if (countryCode == null) {
            // for 3G devices (with SIM) query getNetworkCountryIso()
            countryCode = tm.networkCountryIso
        }
        if (countryCode != null && countryCode.length == 2)
            return countryCode.toLowerCase()
    }

    // if network country not available (tablets maybe), get country code from Locale class
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        countryCode = context.resources.configuration.locales.get(0).country
    } else {
        countryCode = context.resources.configuration.locale.country
    }

    return if (countryCode != null && countryCode.length == 2) countryCode.toLowerCase() else "us"
}