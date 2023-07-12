package com.example.stunting.helper

import android.content.Context
import com.example.stunting.SqliteHelper.DBHelper
import com.example.stunting.response.Stunting
import java.lang.Math.PI
import java.lang.Math.exp
import java.lang.Math.pow
import java.lang.Math.sqrt
import kotlin.math.roundToInt

class naivebayes(umur_: Int, tinggi_: Float, berat_:Float,context: Context) {
    lateinit var dataUji: List<Data2>
    lateinit var datalatih: List<Data2>
    var umur = umur_
    var tinggi = tinggi_
    var berat = berat_
    val context = context

    // constructor
    fun naivebayes() {
        val db = DBHelper(context)
        this.dataUji = db.getData2()
        this.datalatih = listOf(
            Data2(18.0, 80.0, 10.0, "Tidak"),
            Data2(24.0, 85.0, 11.0, "Tidak"),
            Data2(30.0, 92.0, 12.0, "Tidak"),
            Data2(36.0, 97.0, 14.0, "Tidak"),
            Data2(42.0, 101.0, 16.0, "Tidak"),
            Data2(48.0, 106.0, 18.0, "Tidak"),
            Data2(54.0, 110.0, 20.0, "Tidak"),
            Data2(60.0, 115.0, 22.0, "Tidak"),
            Data2(18.0, 75.0, 9.0, "Tidak"),
            Data2(24.0, 80.0, 10.0, "Tidak"),
            Data2(30.0, 87.0, 11.0, "Tidak"),
            Data2(36.0, 93.0, 13.0, "Tidak"),
            Data2(42.0, 98.0, 15.0, "Tidak"),
            Data2(48.0, 103.0, 17.0, "Tidak"),
            Data2(54.0, 107.0, 19.0, "Tidak"),
            Data2(60.0, 112.0, 21.0, "Tidak"),
            Data2(18.0, 70.0, 8.0, "Tidak"),
            Data2(24.0, 75.0, 9.0, "Tidak"),
            Data2(30.0, 82.0, 10.0, "Tidak"),
            Data2(36.0, 88.0, 12.0, "Tidak"),
            Data2(42.0, 93.0, 14.0, "Tidak"),
            Data2(48.0, 98.0, 16.0, "Tidak"),
            Data2(54.0, 102.0, 18.0, "Tidak"),
            Data2(60.0, 107.0, 20.0, "Tidak"),
            Data2(18.0, 65.0, 7.0, "Tidak"),
            Data2(24.0, 70.0, 8.0, "Tidak"),
            Data2(4.0, 50.0, 3.5, "Tidak"),
            Data2(7.0, 60.0, 6.0, "Tidak"),
            Data2(10.0, 70.0, 8.0, "Tidak"),
            Data2(12.0, 75.0, 9.0, "Tidak"),
            Data2(15.0, 80.0, 10.0, "Tidak"),
            Data2(18.0, 85.0, 11.0, "Tidak"),
            Data2(20.0, 90.0, 12.0, "Tidak"),
            Data2(24.0, 95.0, 13.0, "Tidak"),
            Data2(27.0, 100.0, 15.0, "Tidak"),
            Data2(30.0, 105.0, 17.0, "Tidak"),
            Data2(33.0, 110.0, 19.0, "Tidak"),
            Data2(36.0, 115.0, 21.0, "Tidak"),
            Data2(4.0, 50.0, 3.5, "Tidak"),
            Data2(7.0, 60.0, 6.0, "Tidak"),
            Data2(10.0, 70.0, 8.0, "Tidak"),
            Data2(12.0, 75.0, 9.0, "Tidak"),
            Data2(15.0, 80.0, 10.0, "Ya"),
            Data2(18.0, 85.0, 11.0, "Ya"),
            Data2(20.0, 90.0, 12.0, "Ya"),
            Data2(24.0, 95.0, 13.0, "Ya"),
            Data2(27.0, 100.0, 15.0, "Ya"),
            Data2(2.0, 58.0, 4.5, "Ya"),
            Data2(3.0, 62.0, 5.3, "Tidak"),
            Data2(4.0, 64.0, 6.2, "Tidak"),
            Data2(5.0, 68.0, 7.1, "Tidak"),
            Data2(6.0, 71.0, 8.0, "Tidak"),
            Data2(7.0, 73.0, 8.8, "Tidak"),
            Data2(8.0, 75.0, 9.5, "Tidak"),
            Data2(9.0, 77.0, 10.1, "Tidak"),
            Data2(10.0, 79.0, 10.8, "Tidak"),
            Data2(11.0, 81.0, 11.4, "Tidak"),
            Data2(12.0, 83.0, 12.0, "Tidak"),
            Data2(13.0, 85.0, 12.5, "Tidak"),
            Data2(14.0, 87.0, 13.0, "Tidak"),
            Data2(15.0, 89.0, 13.5, "Tidak"),
            Data2(16.0, 91.0, 14.0, "Tidak"),
            Data2(17.0, 93.0, 14.4, "Tidak"),
            Data2(18.0, 95.0, 14.9, "Tidak"),
            Data2(19.0, 97.0, 15.3, "Tidak"),
            Data2(20.0, 99.0, 15.7, "Tidak"),
            Data2(21.0, 101.0, 16.1, "Tidak"),
            Data2(22.0, 103.0, 16.5, "Tidak"),
            Data2(23.0, 105.0, 16.9, "Tidak"),
            Data2(24.0, 107.0, 17.3, "Tidak"),
            Data2(25.0, 109.0, 17.7, "Tidak"),
            Data2(12.0, 80.0, 10.0, "Tidak"),
            Data2(18.0, 85.0, 11.0, "Tidak"),
            Data2(24.0, 90.0, 12.0, "Tidak"),
            Data2(30.0, 95.0, 13.0, "Tidak"),
            Data2(36.0, 100.0, 14.0, "Tidak"),
            Data2(42.0, 105.0, 15.0, "Tidak"),
            Data2(48.0, 110.0, 16.0, "Tidak"),
            Data2(54.0, 115.0, 17.0, "Tidak"),
            Data2(60.0, 120.0, 18.0, "Tidak"),
            Data2(12.0, 75.0, 9.0, "Tidak"),
            Data2(18.0, 80.0, 10.0, "Tidak"),
            Data2(24.0, 85.0, 11.0, "Tidak"),
            Data2(30.0, 90.0, 12.0, "Tidak"),
            Data2(36.0, 95.0, 13.0, "Tidak"),
            Data2(42.0, 100.0, 14.0, "Tidak"),
            Data2(48.0, 105.0, 15.0, "Tidak"),
            Data2(54.0, 110.0, 16.0, "Tidak"),
            Data2(60.0, 115.0, 17.0, "Tidak"),
            Data2(12.0, 78.0, 10.0, "Tidak"),
            Data2(18.0, 83.0, 11.0, "Tidak"),
            Data2(24.0, 88.0, 12.0, "Tidak"),
            Data2(30.0, 93.0, 13.0, "Tidak"),
            Data2(36.0, 98.0, 14.0, "Tidak"),
            Data2(42.0, 103.0, 15.0, "Tidak"),
            Data2(48.0, 108.0, 16.0, "Tidak"),
            Data2(54.0, 113.0, 17.0, "Tidak"),
            Data2(6.0, 65.0, 7.2, "Ya"),
            Data2(8.0, 68.0, 7.8, "Tidak"),
            Data2(10.0, 71.0, 8.5, "Ya"),
            Data2(12.0, 74.0, 9.1, "Ya")
        )
    }

    fun proses(): String {
        val dataLatih = this.datalatih
        val dataUji = this.dataUji
        var persentDataLatih = 20
        var persentDataUji = 100

        val dataLatihBaru = dataLatih.subList(
            0,
            ((persentDataLatih / 100.0) * dataLatih.size).roundToInt()
        )
        val dataUjiBaru = dataUji.subList(
            0,
            ((persentDataUji / 100.0) * dataUji.size).roundToInt()
        )

        val gabungData: List<Data2> = dataLatihBaru + dataUjiBaru

        val jumlahData = gabungData.size
        var jumlahDataTidak = 0
        var jumlahDataYa = 0
        val sumTidak = Data(0.0, 0.0, 0.0)
        val sumYa = Data(0.0, 0.0, 0.0)
        val standarDeviasiTidak = Data(0.0, 0.0, 0.0)
        val standarDeviasiYa = Data(0.0, 0.0, 0.0)

        for (item in gabungData) {
            val stunting = item.stunting
            if (stunting == "Tidak") {
                jumlahDataTidak++
                sumTidak.umur += item.umur
                sumTidak.tinggi += item.tinggi
                sumTidak.berat += item.berat
            } else {
                jumlahDataYa++
                sumYa.umur += item.umur
                sumYa.tinggi += item.tinggi
                sumYa.berat += item.berat
            }
        }

        val probabilitasPriorTidak = jumlahDataTidak.toDouble() / jumlahData.toDouble()
        val probabilitasPriorYa = jumlahDataYa.toDouble() / jumlahData.toDouble()
        val meanTidak = Data(
            sumTidak.umur / jumlahDataTidak.toDouble(),
            sumTidak.tinggi / jumlahDataTidak.toDouble(),
            sumTidak.berat / jumlahDataTidak.toDouble()
        )
        val meanYa = Data(
            sumYa.umur / jumlahDataYa.toDouble(),
            sumYa.tinggi / jumlahDataYa.toDouble(),
            sumYa.berat / jumlahDataYa.toDouble()
        )

        for (item in gabungData) {
            val stunting = item.stunting
            if (stunting == "Tidak") {
                standarDeviasiTidak.umur += pow(item.umur - meanTidak.umur, 2.0)
                standarDeviasiTidak.tinggi += pow(item.tinggi - meanTidak.tinggi, 2.0)
                standarDeviasiTidak.berat += pow(item.berat - meanTidak.berat, 2.0)
            } else {
                standarDeviasiYa.umur += pow(item.umur - meanYa.umur, 2.0)
                standarDeviasiYa.tinggi += pow(item.tinggi - meanYa.tinggi, 2.0)
                standarDeviasiYa.berat += pow(item.berat - meanYa.berat, 2.0)
            }
        }

        standarDeviasiTidak.umur =
            kotlin.math.sqrt(standarDeviasiTidak.umur / (jumlahDataTidak - 1))
        standarDeviasiTidak.tinggi =
            kotlin.math.sqrt(standarDeviasiTidak.tinggi / (jumlahDataTidak - 1))
        standarDeviasiTidak.berat =
            kotlin.math.sqrt(standarDeviasiTidak.berat / (jumlahDataTidak - 1))

        standarDeviasiYa.umur = kotlin.math.sqrt(standarDeviasiYa.umur / (jumlahDataYa - 1))
        standarDeviasiYa.tinggi = kotlin.math.sqrt(standarDeviasiYa.tinggi / (jumlahDataYa - 1))
        standarDeviasiYa.berat = kotlin.math.sqrt(standarDeviasiYa.berat / (jumlahDataYa - 1))

        val probabilitasTidak =
            (1 / sqrt(2 * PI * standarDeviasiTidak.umur)) *
                    kotlin.math.exp(
                        -pow(umur - meanTidak.umur, 2.0) /
                                (2 * pow(standarDeviasiTidak.umur, 2.0))
                    ) *
                    (1 / sqrt(2 * PI * standarDeviasiTidak.tinggi)) *
                    exp(
                        -pow(tinggi - meanTidak.tinggi, 2.0) /
                                (2 * pow(standarDeviasiTidak.tinggi, 2.0))
                    ) *
                    (1 / kotlin.math.sqrt(2 * PI * standarDeviasiTidak.berat)) *
                    exp(
                        -pow(berat - meanTidak.berat, 2.0) /
                                (2 * pow(standarDeviasiTidak.berat, 2.0))
                    ) *
                    probabilitasPriorTidak

        val probabilitasYa =
            (1 / sqrt(2 * PI * standarDeviasiYa.umur)) *
                    kotlin.math.exp(
                        -pow(umur - meanYa.umur, 2.0) /
                                (2 * pow(standarDeviasiYa.umur, 2.0))
                    ) *
                    (1 / sqrt(2 * PI * standarDeviasiYa.tinggi)) *
                    exp(
                        -pow(tinggi - meanYa.tinggi, 2.0) /
                                (2 * pow(standarDeviasiYa.tinggi, 2.0))
                    ) *
                    (1 / sqrt(2 * PI * standarDeviasiYa.berat)) *
                    exp(
                        -pow(berat - meanYa.berat, 2.0) /
                                (2 * pow(standarDeviasiYa.berat, 2.0))
                    ) *
                    probabilitasPriorYa

        val hasil = if (probabilitasTidak > probabilitasYa) "Tidak" else "Ya"

        return hasil
    }

    data class Data(
        var umur: Double,
        var tinggi: Double,
        var berat: Double
    )

    data class Data2(
        var umur: Double,
        var tinggi: Double,
        var berat: Double,
        var stunting: String
    )
}