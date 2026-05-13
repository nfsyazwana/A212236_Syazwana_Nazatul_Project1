package com.example.a212236_syazwana_nazatul_project1.data

data class LocationData(
    val icon: String,
    val name: String,
    val address: String,
    val openTime: String
)

val locations = listOf(
    LocationData("🏢", "Environmental Recovery Centre", "35, Jalan P10/16, Taman Industri Selaman, 43650 Bandar Baru Bangi, Selangor", "Open: 8AM"),
    LocationData("♻️", "Mudajaya Recycle Sdn Bhd", "93, Jalan Taming 6 Taman Taming Jaya Balakong, 43300 Seri Kembangan, Selangor", "Open: 9AM"),
    LocationData("📍", "CS Recycling Sdn Bhd", "Lot 21083, Persiaran Seri Timah, Taman Seri Timah, 43300 Seri Kembangan, Selangor", "Open: 9AM"),
    LocationData("🌱", "Pusat Kitar Semula Bangi, Kajang", "Fasa 5, Taman Tasik Cempaka, Bandar Baru Bangi, Selangor", "Open: 8:30AM"),
    LocationData("♻️", "PJ Eco Recycling Plaza", "Jalan SS8/39, Sungai Way Free Trade Industrial Zone, 47300 Petaling Jaya, Selangor", "Open: Tue-Sun 9AM-4:30PM"),
    LocationData("🏢", "IPC Recycling & Buy-Back Centre", "Level LG1, IPC Shopping Centre, 2, Jalan PJU 7/2, Mutiara Damansara, 47800 Petaling Jaya, Selangor", "Open: 8AM-10PM Daily"),
    LocationData("📍", "Perfect VR Trading (M) Sdn Bhd", "No. 3, Lot 5193A, RP KTM, Jalan Balakong, Kg Baru Balakong, 43300 Seri Kembangan, Selangor", "Open: 9AM"),
    LocationData("🌿", "My Metal Comm, Inc", "Lot 1571, Kg. Sungai Kandis, Jalan Kampung Jawa, Section 36, 40470 Shah Alam, Selangor", "Open: Mon-Sat 8AM-6PM"),
    LocationData("♻️", "USJ Paper Recyclers Sdn Bhd", "Lot 852, Jalan Subang 7, Taman Perindustrian Subang, 47610 Subang Jaya, Selangor", "Open: Mon-Sat 8:30AM-6PM"),
    LocationData("🏢", "STS Trading Sdn Bhd", "Lot 58A, PT 3769, Lorong 3D, Off Jalan 10, Kampung Baru Subang, 41000 Shah Alam, Selangor", "Open: Mon-Sat 9AM-6PM"),
)
