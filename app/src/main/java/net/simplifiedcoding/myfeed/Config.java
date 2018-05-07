package net.simplifiedcoding.myfeed;

/**
 * Created by Belal on 12/5/2015.
 */
public class Config {
    //Data URL
    //public static final String DATA_URL = "http://192.168.8.101/AppProperti/getimage.php?page=";
//    public static final String DATA_URL = "http://terserah.ga/data.php";
//    public static final String LOGIN_URL = "http://terserah.ga/login.php";
//    public static final String URL_ADD = "http://terserah.ga/register.php";
//    public static final String UPLOAD_URL = "http://terserah.ga/uploadimage2.php";
    public static final String DATA_URL = "https://irfanpi.000webhostapp.com/data.php";
//    public static final String DATA_URL = "https://irfanpi.000webhostapp.com/login.php";
    public static final String LOGIN_URL = "https://irfanpi.000webhostapp.com/login.php";
//    public static final String URL_ADD = "http://192.168.43.51/properti/tes/register.php";
//    public static final String UPLOAD_URL = "http://192.168.43.51/properti/tes/uploadimage2.php";
//    public static final String DOKTER_URL = "http://192.168.43.51/properti/tes/dokter.php";
    public static final String URL_ADD = "https://irfanpi.000webhostapp.com/register.php";
    public static final String UPLOAD_URL = "https://irfanpi.000webhostapp.com/uploadimage2.php";
    public static final String DOKTER_URL = "https://irfanpi.000webhostapp.com/dokter.php";
    public static final String DOKTER_URL2 = "https://irfanpi.000webhostapp.com/haversine.php?lat=";
    public static final String BERITA_URL = "https://irfanpi.000webhostapp.com/berita.php?date=";
    public static final String LOGIN_ADMIN = "https://irfanpi.000webhostapp.com/admin.php";


    //JSON TAGS
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    public static final String TAG_PUBLISHER = "price";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_LOCATIONLAT = "lat";
    public static final String TAG_LOCATIONLNG = "lng";
    public static final String TAG_TELEPHONE = "telephone";
    public static final String TAG_ADDRESS = "address";

    //JSON TAGS DOKTER
    public static final String TAG_IMAGE_DOKTER = "image";
    public static final String TAG_NAME_DOKTER = "nama";
    public static final String TAG_ALAMAT_DOKTER = "alamat";
    public static final String TAG_TELEPON_DOKTER = "telpon";
    public static final String TAG_RATING_DOKTER = "rating";
    public static final String TAG_JAMBUKA_DOKTER = "jambuka";
    public static final String TAG_JAMTUTUP_DOKTER = "jamtutup";
    public static final String TAG_JARAK = "jarak";

    //LOGIN
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    //SIGNUP
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_USERNAME = "username"; //username itu variabel untuk username
    public static final String KEY_EMP_PASSWORD = "password";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String LOGIN_SUCCESS = "success";

    //JSON BERITA
    public static final String KEY_JUDUL = "judul";
    public static final String KEY_ISI = "isi";
    public static final String KEY_TANGGAL = "tanggal";

    //LOGIN
    public static final String KEY_USERNAME_ADMIN = "username";
    public static final String KEY_PASSWORD_ADMIN = "password";
    public static final String LOGIN_SUCCESS_ADMIN = "success";
}

//"http://simplifiedcoding.16mb.com/feed/feed.php?page="