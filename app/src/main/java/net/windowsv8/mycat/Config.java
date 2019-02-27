package net.windowsv8.mycat;

/**
 * Created by Belal on 12/5/2015.
 */
public class Config {

    public static final String DATA_URL = "https://irfanpi.000webhostapp.com/data.php";
    public static final String LOGIN_URL = "https://irfanpi.000webhostapp.com/login.php";
    public static final String URL_ADD = "https://irfanpi.000webhostapp.com/register.php";
    public static final String UPLOAD_URL = "https://irfanpi.000webhostapp.com/uploadimage2.php";
    public static final String DOKTER_URL = "https://irfanpi.000webhostapp.com/dokter.php";
    public static final String PETSHOP_URL = "https://irfanpi.000webhostapp.com/haversine.php?lat=";
    public static final String BERITA_URL = "https://irfanpi.000webhostapp.com/berita.php?date=";
    public static final String LOGIN_ADMIN = "https://irfanpi.000webhostapp.com/admin.php";
    public static final String UPLOAD_BERITA = "https://irfanpi.000webhostapp.com/uploadberita.php";
    public static final String DELETE_DATA_URL = "https://irfanpi.000webhostapp.com/hapusdata.php?id=";

    //JSON TAGS
    public static final String TAG_ID_IMAGE = "id";
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    public static final String TAG_PUBLISHER = "price";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_TELEPHONE = "telephone";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_JENIS = "jenis";
    public static final String TAG_USER = "user";

    //JSON TAGS DOKTER
    public static final String TAG_IMAGE_PETSHOP = "image";
    public static final String TAG_NAME_PETSHOP = "nama";
    public static final String TAG_ALAMAT_PETSHOP = "alamat";
    public static final String TAG_TELEPON_PETSHOP = "telpon";
    public static final String TAG_RATING_PETSHOP = "rating";
    public static final String TAG_JAMBUKA_PETSHOP = "jambuka";
    public static final String TAG_JAMTUTUP_PETSHOP = "jamtutup";
    public static final String TAG_JARAK_PETSHOP = "jarak";
    public static final String TAG_DESKRIPSI_PETSHOP = "deskripsi";
    public static final String TAG_LAT_PETSHOP = "lat";
    public static final String TAG_LNG_PETSHOP = "lng";
    public static final String TAG_JADWAL_PETSHOP = "jadwal";

    //LOGIN
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    //SIGNUP
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_USERNAME = "username";
    public static final String KEY_EMP_PASSWORD = "password";
    public static final String KEY_EMP_EMAIL = "email";
    public static final String LOGIN_SUCCESS = "success";

    //JSON BERITA
    public static final String KEY_JUDUL = "judul";
    public static final String KEY_ISI = "isi";
    public static final String KEY_TANGGAL = "tanggal";
    public static final String KEY_TEMPAT = "tempat";
    public static final String KEY_CONTACT = "nomor";

    //LOGIN
    public static final String KEY_USERNAME_ADMIN = "username";
    public static final String KEY_PASSWORD_ADMIN = "password";
    public static final String LOGIN_SUCCESS_ADMIN = "success";

}
