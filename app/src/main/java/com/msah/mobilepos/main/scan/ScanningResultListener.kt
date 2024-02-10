package com.msah.mobilepos.main.scan

interface ScanningResultListener {
    fun onScanned(result: String)
}