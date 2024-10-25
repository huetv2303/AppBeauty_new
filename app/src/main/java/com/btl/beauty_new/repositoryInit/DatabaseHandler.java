package com.btl.beauty_new.repositoryInit;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.btl.beauty_new.R;
import com.btl.beauty_new.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "beauty_new.sqlite";
    private static final Integer DATABASE_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;
    private final Context context;

    // List Sample DataSS
    private List<User> userList;
    private List<Store> storeList;
    private List<StoreSaved> storeSavedList;
    private List<Cosmetic> cosmeticList;
    private List<CosmeticSize> cosmeticSizeList;
    private List<Notify> notifyList;
    private List<NotifyToUser> notifyToUsers;
    private List<Order> orderList;
    private List<OrderDetail> orderDetailList;
    private List<CosmeticSaved> cosmeticSavedList;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, DATABASE_FACTORY, DATABASE_VERSION);
        this.context = context;
    }

    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Cursor getDataRow(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        return c;
    }

    // Convert ảnh thành một mảng byte
    public byte[] convertDrawableToByteArray(Drawable drawable) {
        // Convert khi đúng cấu trúc bitmap
        if (drawable instanceof BitmapDrawable) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        // Không cùng cấu trúc bitmap
        final int width = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ? drawable
                .getBounds().height() : drawable.getIntrinsicHeight();

        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width,
                height <= 0 ? 1 : height, Bitmap.Config.ARGB_8888);

        // Vẽ hình
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        // Chuyển kiểu bitmap thành byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // convert từ một mảng byte thành một bitmap
    public static Bitmap convertByteArrayToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // insert data into list
    private void SampleData() {
        // region User
        userList = new ArrayList<>();
        userList.add(new User(1, "Nguyễn Văn Long", "Male", "12-04-2004", "0828007853", "long", "123456"));
        userList.add(new User(2, "Trần Gia Bảo", "Male", "17-04-2004", "0947679750", "bao", "123456"));
        userList.add(new User(3, "Tưởng Văn Huế", "Male", "25-06-2004", "0388891635", "hue", "123456"));
        userList.add(new User(4, "Vương Đức Cường", "Male", "25-06-2004", "0388891635", "cuong", "123456"));

        // region store
        storeList = new ArrayList<>();
        storeList.add(new Store(1, "Cửa Hàng MAC", "Số 24/63 Phùng Khoang, Trung Văn, Nam Từ Liêm",
                "0828007853", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.store_mac, null))));
        storeList.add(new Store(2, "Cửa Hàng NARS", "Số 16/84 Hồ Tùng Mậu, Cầu Giấy, Hà Nội",
                "0885438847", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.store_nars, null))));
        storeList.add(new Store(3, "Cửa Hàng Neutrogena", "Số 44/132 Q.Cầy Giấy, P.Quang Hoa, Hà Nội",
                "0559996574", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.store_neutrogena, null))));
        storeList.add(new Store(4, "Cửa Hàng Olay", "Số 184 Phùng Khoang, Trung Văn, Nam Từ Liêm",
                "0141670738", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.store_olay, null))));
        storeList.add(new Store(5, "Cửa Hàng Shiseido", "105 Thanh Xuân Bắc, Q.Thanh Xuân, Hà Nôi",
                "0627724695", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.store_shiseido, null))));
        storeList.add(new Store(6, "Cửa Hàng Thebodyshop", "Royal City 72A Nguyễn Trãi, Nam Từ Liêm",
                "0828007853", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.store_thebodyshop, null))));

        // region storesaved
        storeSavedList = new ArrayList<>();
        storeSavedList.add(new StoreSaved(1, 3));
        storeSavedList.add(new StoreSaved(4, 3));
        storeSavedList.add(new StoreSaved(1, 1));
        storeSavedList.add(new StoreSaved(1, 2));
        storeSavedList.add(new StoreSaved(2, 2));
        storeSavedList.add(new StoreSaved(6, 3));

        // region cosmetic
        cosmeticList = new ArrayList<>();
        // store Shiseido
        cosmeticList.add(new Cosmetic(1, "Tinh chất dưỡng da", "Dưỡng da",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_shi, null)),
                "Serum ULTIMUNE đoạt nhiều giải thưởng, giúp củng cố hàng rào bảo vệ da mạnh mẽ, phục hồi độ đàn hồi của da. Trải nghiệm sự rạng rỡ, mịn màng và đàn hồi, cho làn da Khỏe khoắn, Tươi trẻ:\n" +
                        "\n" +
                        "Loại da:\n" +
                        "\n" +
                        "\t\tTất cả loại da / Da dầu / Da hỗn hợp / Da khô\n" +
                        "\nMùi hương:\n" +
                        "\n" +
                        "\t\t" +
                        "Hương thơm hoa cỏ mang đến cảm giác thư giãn và tăng cường năng lượng\n" +
                        "Chứa hợp chất ImuCalm Compound™ ngăn ngừa các tổn thương do căng thẳng cảm xúc.\n" +
                        "\n" +
                        "Thời gian sử dụng:\n" +
                        "\n" +
                        "\t\t" +
                        "Khoảng 2 tháng (với liều lượng khuyên dùng)\n" +
                        "\n" +
                        "Kết cấu :\n" +
                        "\n" +
                        "\t\t" +
                        "Kết cấu sánh mịn nhưng trong mướt dễ dàng thẩm thấu trên toàn bộ khuôn mặt và vùng da mắt.\n" +
                        "Cảm giác mịn mượt như satin đọng lại trên da, như thể làn da được bảo vệ bởi một tấm màn vô hình.\n", 5));

        cosmeticList.add(new Cosmetic(1, "Sữa rửa mặt SHISEIDO", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_shi1, null)),
                "Rạng rỡ tinh tế. " +
                        "\t\t Sữa rửa mặt có chứa Bột trắng siêu mịn và đất sét trắng để loại bỏ các tạp chất một cách hiệu quả. Giàu dưỡng chất. Bọt êm mịn. Loại bỏ các tế" +
                        " bào da xỉn màu ở bề mặt, các chất gây ô nhiễm và các chất bị ôxi hóa gây nên lão hóa. Được làm từ những nguyên liệu có khả năng dưỡng ẩm sâu, không gây khô da, củng cố khả năng bảo vệ từ bên trong.\n" +
                        "\t\t Phù hợp cho mọi loại da.\n" +
                        "\n\n- Cách dùng: sữa rửa mặt\n" +
                        "\t\t + Thêm một it nước, tạo bọt trên sản phẩm" +
                        "\t\t + Massage nhẹ nhàng toàn gương mặt" +
                        "\t\t + Rửa mặt lại thật sạch bằng nước", 5));

        cosmeticList.add(new Cosmetic(1, "Kem dưỡng ban ngày", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_shi2, null)),
                "Kem dưỡng cao cấp ban ngày đa chức năng với kết cấu mịn mượt giàu ẩm giúp duy trì cảm giác ẩm mượt tối ưu suốt cả ngày. Công nghệ" +
                        " độc quyền của Shiseido kích thích Nhân tố bảo vệ da ban ngày để mang đến làn da rạng rỡ từ bên trong. Làn da được bảo vệ ngay cả khi " +
                        "bị tác động bởi những yếu tố gây hại như tia UV, môi trường khô, ôxi hóa và sự ô nhiễm. Có thể sử dụng dưới lớp trang điểm.:\n" +
                        "\t\t Bảo vệ da với SPF 20.\n" +
                        "\t\t+ Tổng quan:\n" +
                        "\t\t  VẺ ĐẸP VƯỢT THỜI GIAN\n" +
                        "10 NĂM TRÂN QUÝ VẺ ĐẸP.\n" +
                        "10 NĂM KHÁM PHÁ RA BÁU VẬT CỦA NHẬT BẢN: THẢO DƯỢC ENMEI.\n" +
                        "10 NĂM CỦNG CỐ VẺ ĐẸP VĨNH CỬU, LÀN DA ĐÀN HỒI VÀ TỎA SÁNG RỰC RỠ.\n" +
                        "\t\t Thành phần chính:" +
                        "\t\t  PHỨC HỢP SKINGENECELL ENMEI\n" +
                        "Thảo dược từ xa xưa nay trở thành nguồn năng lượng sống mới cho làn da. Tìm thấy sâu trong rừng thiêng nơi ngọn núi Koya huyền bí, loài " +
                        "thảo mộc đã được các vị thiền sư sử dụng hàng thế kỷ nay. Các nghiên cứu hiện đại đã giúp chạm đến và khai thác sức mạnh chống lão hóa và duy trì vẻ đẹp vượt trội cùa loài thảo mộc huyền bí.\n", 5));

        cosmeticList.add(new Cosmetic(1, "Kem dưỡng mắt", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_shi3, null)),
                "Kem dưỡng mắt chuyên sâu, giảm rõ rệt quầng thâm mắt, cho vẻ ngoài trẻ trung đầy sức sống." +
                        "\n\n  Thành phần chính:\n" +
                        "\t\t+  WATER (AQUA)･DIMETHICONE･BUTYLENE GLYCOL･GLYCERIN･ALCOHOL･DIMETHICONE/VINYL DIMETHICONE CROSSPOLYMER･MYRISTYL MYRISTATE･PETROLATUM･" +
                        "HYDROGENATED POLYDECENE･BEHENYL ALCOHOL･CETYL ETHYLHEXANOATE･GLYCERYL STEARATE SE･POTASSIUM METHOXYSALICYLATE･POLYMETHYL METHACRYLATE･STEARYL " +
                        "ALCOHOL･DIMETHYLACRYLAMIDE/SODIUM ACRYLOYLDIMETHYLTAURATE CROSSPOLYMER･POLYSORBATE 60･PEG-40 STEARATE･TOCOPHERYL ACETATE･PHENOXYETHANOL･TITANIUM" +
                        " DIOXIDE (CI 77891)･FRAGRANCE (PARFUM)･SORBITAN TRISTEARATE･TRISODIUM EDTA･PEG-10 DIMETHICONE･SODIUM CITRATE･ORYZANOL･2-O-ETHYL ASCORBIC " +
                        "ACID･MICA･SILICA･XANTHAN GUM･SODIUM METAPHOSPHATE･LIMONENE･CITRIC ACID･PEG/PPG-14/7 DIMETHYL ETHER･ALUMINUM HYDROXIDE･BENZYL BENZOATE･HYDROXYISOHEXYL" +
                        " 3-CYCLOHEXENE CARBOXALDEHYDE･IRON OXIDES (CI 77491)･SODIUM HYALURONATE･LINALOOL･CRATAEGUS MONOGYNA FLOWER EXTRACT･TOCOPHEROL･\n" +
                        "\n\n- Cách dùng: kem dưỡng mắt\n" +
                        "\t\t + dàn trải một lượng nhỏ sản phẩm quanh vùng mắt." +
                        "\t\t + Thoa đều khắp mi mắt và vùng da dưới mắt." +
                        "\t\t + Massage nhẹ nhàng theo hướng từ gò mà lên.", 5));

        cosmeticList.add(new Cosmetic(1, "Kem chống nắng dưỡng da", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_shi4, null)),
                "huyển hóa một phần ánh nắng mặt trời thành ánh sáng sinh học với Công nghệ Sun Dual Care™. " +
                        "\n\n Sản phẩm chống nắng đầu tiên giúp bảo vệ da khỏi tia UV, đồng thời, chuyển hóa một phần ánh sáng mặt trời thành ánh sáng có lợi, tối ưu hiệu quả chăm sóc da.\n" +
                        "\t\t Phù hợp để dùng hằng ngày với kết cấu nhẹ tênh, không dầu, với thành phần các chất chống ôxi hóa và hyaluronic acid, sản phẩm giúp bảo vệ da khỏi các yếu tố " +
                        "ô nhiễm môi trường gây sạm hoặc khô da, để lại một làn da ẩm mịn, tươi sáng.\n" +
                        "\t\t SPF 50.\n" +
                        "\t\t Tinh chất Spirulina Energy Essence\n" +
                        "Chuyển hóa một phần ánh nắng mặt trời thành ánh sáng có lợi\n" +
                        "\t\t Ít bị rửa trôi\n" +
                        "So sánh với các sản phẩm chống nắng\n" +
                        "không kháng nước và công thức kháng nước lưu lại trên da\n" +
                        "\t\t Không gây bết dính", 5));

        cosmeticList.add(new Cosmetic(1, "Mặt nạ ban đêm", "Mặt nạ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_shi5, null)),
                "Đánh thức giác quan của làn da. Toả sáng rạng rỡ từ bên trong." +
                        "\t\t Ứng dụng khoa học hệ thần kinh của các giác quan trên làn da. Chất gel giàu dưỡng chất, êm ái trên da, áp dụng công nghệ ReNeura Technology+™ gửi các tín hiệu cảm biến bên trong làn da, giúp da phản ứng tối ưu đến hiệu quả chống lão hoá của sản phẩm. \n" +
                        "\t\t Tập trung vào quá trình hoạt động ban đêm của làn da, kem dưỡng cung cấp dưỡng chất tràn đầy, giúp giải quyết các tổn thương xuất hiện ban ngày, ngay trong giấc ngủ của bạn. \n" +
                        "\t\t Làm sáng rõ rệt, loại bỏ đốm nâu, làm đều màu da, và xoá những vết nhăn nhỏ. Giúp bạn toả sáng.\n", 5));

        // store mac
        cosmeticList.add(new Cosmetic(1, "SRM Cerave dạng chai", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_cerave1, null)),
                "Sữa rửa mặt Cerave - sản phẩm hoàn hảo cho làn da dầu siêu nhạy cảm:\n" +
                        "\n" +
                        "Thành phần & công dụng chính:\n" +
                        "\n" +
                        "\t\tCeramides - giúp duy trì lớp màng bảo vệ da, giúp tăng dẻo dai, khỏe mạnh, chống tia cực tím cho da\n" +
                        "\t\tHyaluoronic acid - tạo lớp dưỡng ẩm tự nhiên trên da, giúp dưỡng da mềm mịn và tránh tình trạng lão hóa sớm\n" +
                        "\t\tNiacinamide - giúp da tự sản sinh thêm ceramides và các acid béo cần thiết để tự duy trì và phục hồi màng bảo vệ da, giúp hỗ trợ làm trắng da, phòng ngừa lão hóa và giảm mụn\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\n" +
                        "\t\tLàm ẩm da với nước và cho một lượng sản phẩm vừa đủ ra lòng bàn tay. Thoa sản phẩm lên mặt nhẹ nhàng massage trong vòng 1 phút. Tránh tiếp xúc sản phẩm với mắt, nếu có, rửa kỹ lại với nước\n", 1));

        cosmeticList.add(new Cosmetic(1, "SRM Cerave dạng chai nhỏ", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_cerave2, null)),
                "\n" +
                        "Thành phần & công dụng chính:\n" +
                        "\n" +
                        "\t\tCeramides - giúp duy trì lớp màng bảo vệ da, giúp tăng dẻo dai, khỏe mạnh, chống tia cực tím cho da\n" +
                        "\t\tHyaluoronic acid - tạo lớp dưỡng ẩm tự nhiên trên da, giúp dưỡng da mềm mịn và tránh tình trạng lão hóa sớm\n" +
                        "\t\tNiacinamide - giúp da tự sản sinh thêm ceramides và các acid béo cần thiết để tự duy trì và phục hồi màng bảo vệ da, giúp hỗ trợ làm trắng da, phòng ngừa lão hóa và giảm mụn\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\n" +
                        "\t\tLàm ẩm da với nước và cho một lượng sản phẩm vừa đủ ra lòng bàn tay. Thoa sản phẩm lên mặt nhẹ nhàng massage trong vòng 1 phút. Tránh tiếp xúc sản phẩm với mắt, nếu có, rửa kỹ lại với nước\n", 1));

        cosmeticList.add(new Cosmetic(1, "Kem dưỡng ẩm Cerave", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_cerave3, null)),
                ":\n" +
                        "Thành Phần:\n" +
                        "\n" +
                        "\t\tAqua/Water/Eau, Glycerin, Cetearyl Alcohol, Caprylic/Capric Triglyceride, Cetyl Alcohol, Ceteareth-20, Petrolatum , Potassium Phosphate," +
                        " Ceramide Np, Ceramide Ap, Ceramide Eop, Carbomer, Dimethicone, Behentrimonium Methosulfate, Sodium Lauroyl Lactylate, Sodium Hyaluronate," +
                        " Cholesterol, Phenoxyethanol, Disodium Edta, Dipotassium Phosphate, Tocopherol, Phytosphingosine, Xanthan Gum, Ethylhexylglycerin\n" +
                        "\t\tCông dụng:\n" +
                        "\t\tThích hợp cho da khô và rất khô sử dụng cho mặt và cơ thể\n" +
                        "\t\tGiúp khôi phục và duy trì hàng rào bảo vệ tự nhiên của da\n" +
                        "\t\tGiúp duy trì độ ẩm tự nhiên của da\n" +
                        "\t\tKhông gây mụn, không có mùi thơm\n" +
                        "\t\tĐược phát triển bởi các bác sĩ da liễu\n" +
                        "\t\tĐỐI TƯỢNG SỬ DỤNG: Phù hợp với da khô, cần dưỡng ẩm\n" +
                        "\nHướng dấn sử dụng:\n" +
                        "\t\tSử dụng ngày 2 lần sáng và tối", 1));

        cosmeticList.add(new Cosmetic(1, "SRM Cetaphil", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_cetaphil1, null)),
                ":\n" +
                        "Thành Phần:\n" +
                        "\n" +
                        "\t\tCetyl Alcohol và Stearyl Alcohol: Là hai loại cồn béo có tác dụng làm mềm da và cải thiện độ đàn hồi cho da rất tốt. Cetyl Alcohol và Stearyl Alcohol đều rất" +
                        " lành tính với da nên được sử dụng rất phổ biến trong các sản phẩm dưỡng da.\n" +
                        "\t\tSodium Lauryl Sulfate: Có công dụng làm sạch nhẹ nhàng các bụi bẩn, bã nhờn dư thừa và lớp trang điểm còn sót lại, giúp da luôn khô thoáng và sạch sẽ.\n" +
                        "\t\tPropylene Glycol: Là thành phần ngăn sự thoát hơi nước trên bề mặt da, có công dụng hydrat hóa và cung cấp độ ẩm cho da.\n" +
                        "\t\tĐặc biệt, sữa rửa mặt không chứa xà phòng, không chứa hương liệu và các chất bảo quản nên đảm bảo an toàn cho da và không gây kích ứng cho da, dù là da nhạy cảm.\n" +
                        "\nHướng dấn sử dụng:\n" +
                        "\t\tDùng ướt:\n" +
                        "\t\tĐầu tiên, làm ướt da mặt với nước\n" +
                        "\t\tSau đó cho một lượng sản phẩm vừa đủ và thoa đều lên mặt, kết hợp thực hiện massage nhẹ nhàng trong khoảng 2 – 3 phút để thư giãn cũng như giúp loại bỏ bụi bẩn bám trên da một cách nhẹ nhàng.\n" +
                        "\t\tCuối cùng, rửa sạch lại với nước và dùng khăn hoặc bông cotton nhẹ nhàng thấm khô nước trên mặt.\n" +
                        "\t\tDùng khô:\n" +
                        "\t\tCho sữa rửa mặt lên da và mát-xa nhẹ nhàng để hoà tan bụi bẩn, tạp chất.\n" +
                        "\t\tDùng miếng vải mềm hoặc bông cotton để lau đi lượng sữa rửa mặt còn dư. Nhờ đó, trên da để lại trên làn da một màng sữa rửa mặt Cetaphil mỏng giữ cho da mềm và ẩm suốt cả ngày.\n" +
                        "\t\t", 1));

        cosmeticList.add(new Cosmetic(1, "SRM Bioderma 2024 lọ nhỏ mới", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_bio1, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER/EAU, SODIUM COCOAMPHOACETATE, SODIUM LAURETH SULFATE, METHYLPROPANEDIOL, DISODIUM EDTA, MANNITOL, " +
                        "XYLITOL, RHAMNOSE, FRUCTOOLIGOSACCHARIDES, ZINC SULFATE, COPPER SULFATE, GINKGO BILOBA LEAF EXTRACT, PEG-90 GLYCERYL ISOSTEARATE, " +
                        "LACTIC ACID , LAURETH-2, POTASSIUM SORBATE, SODIUM CHLORIDE, PROPYLENE GLYCOL, SODIUM HYDROXIDE, FRAGRANCE (PARFUM). [BI 418]\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\tDùng mỗi sáng và tối:\n" +
                        "\t\t- Lấy một ít sản phẩm lên tay ướt đã rửa sạch và tạo bọt \n" +
                        "\t\t- Massage nhẹ nhàng lên vùng da đã làm ướt rồi rửa lại thật sạch\n" +
                        "\t\t- Lau khô nhẹ nhàng bằng khăn sạch\n", 1));

        cosmeticList.add(new Cosmetic(1, "SRM Bioderma 2024 dạng chai mới", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_bio2, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER/EAU, SODIUM COCOAMPHOACETATE, SODIUM LAURETH SULFATE, METHYLPROPANEDIOL, DISODIUM EDTA, MANNITOL, " +
                        "XYLITOL, RHAMNOSE, FRUCTOOLIGOSACCHARIDES, ZINC SULFATE, COPPER SULFATE, GINKGO BILOBA LEAF EXTRACT, PEG-90 GLYCERYL ISOSTEARATE, " +
                        "LACTIC ACID , LAURETH-2, POTASSIUM SORBATE, SODIUM CHLORIDE, PROPYLENE GLYCOL, SODIUM HYDROXIDE, FRAGRANCE (PARFUM). [BI 418]\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\tDùng mỗi sáng và tối:\n" +
                        "\t\t- Lấy một ít sản phẩm lên tay ướt đã rửa sạch và tạo bọt \n" +
                        "\t\t- Massage nhẹ nhàng lên vùng da đã làm ướt rồi rửa lại thật sạch\n" +
                        "\t\t- Lau khô nhẹ nhàng bằng khăn sạch\n", 1));

        cosmeticList.add(new Cosmetic(1, "Tẩy trang Bioderma", "Tẩy trang",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_bio3, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER/EAU, SODIUM COCOAMPHOACETATE, SODIUM LAURETH SULFATE, METHYLPROPANEDIOL, DISODIUM EDTA, MANNITOL, " +
                        "XYLITOL, RHAMNOSE, FRUCTOOLIGOSACCHARIDES, ZINC SULFATE, COPPER SULFATE, GINKGO BILOBA LEAF EXTRACT, PEG-90 GLYCERYL ISOSTEARATE, " +
                        "LACTIC ACID , LAURETH-2, POTASSIUM SORBATE, SODIUM CHLORIDE, PROPYLENE GLYCOL, SODIUM HYDROXIDE, FRAGRANCE (PARFUM). [BI 418]\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\tDùng mỗi sáng và tối:\n" +
                        "\t\t1. Làm ướt bông tẩy trang với dung dịch Sensibio H20 \n" +
                        "\t\t2. Nhẹ nhàng làm sạch vùng mặt và mắt\n" +
                        "\t\t3. Thực hiện lại thao tác trên cho đến khi không thấy bám bẩn trên bông tẩy trang\n" +
                        "\t\t4. Không cần rửa lại với nước\n", 1));

        cosmeticList.add(new Cosmetic(1, "SRM Bioderma 2023 cũ", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_bio4, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER, SODIUM LAURETH SULFATE, PEG-8, COCO-BETAINE, HEXYLENE GLYCOL, ZINC PCA, SODIUM CHLORIDE, " +
                        "CITRIC ACID, SODIUM HYDROXIDE, PHENOXYETHANOL, CAPRYLYL GLYCOL, SODIUM BENZOATE, PARFUM/FRAGRANCE.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\tDùng mỗi sáng và tối:\n" +
                        "\t\t- Làm ướt da mặt bằng nước ấm\n" +
                        "\t\t- Lấy một lượng sản phẩm vừa đủ ra lòng bàn tay\n" +
                        "\t\t- Tạo bọt và mát-xa nhẹ nhàng lên mặt, tránh vùng mắt\n" +
                        "\t\t- Rửa lại bằng nước sạch và lau khô\n", 1));

        cosmeticList.add(new Cosmetic(1, "SRM La Roche-Posay 2024 mới", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_laroche1, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER, SODIUM LAURETH SULFATE, PEG-8, COCO-BETAINE, HEXYLENE GLYCOL, ZINC PCA, SODIUM CHLORIDE, " +
                        "CITRIC ACID, SODIUM HYDROXIDE, PHENOXYETHANOL, CAPRYLYL GLYCOL, SODIUM BENZOATE, PARFUM/FRAGRANCE.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\tDùng mỗi sáng và tối:\n" +
                        "\t\t- Làm ướt da mặt bằng nước ấm\n" +
                        "\t\t- Lấy một lượng sản phẩm vừa đủ ra lòng bàn tay\n" +
                        "\t\t- Tạo bọt và mát-xa nhẹ nhàng lên mặt, tránh vùng mắt\n" +
                        "\t\t- Rửa lại bằng nước sạch và lau khô\n", 1));

        cosmeticList.add(new Cosmetic(1, "KCN La Roche-Posay", "Kem chống nắng",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_laroche2, null)),
                "Thành phần:\n" +
                        "\t\tAQUA / WATER, HOMOSALATE, ETHYLHEXYL SALICYLATE, SILICA, BIS-ETHYLHEXYLOXYPHENOL METHOXYPHENYL TRIAZINE, BUTYL METHOXYDIBENZOYLMETHANE, " +
                        "C12-15 ALKYL BENZOATE, ETHYLHEXYL TRIAZONE, ALCOHOL DENAT., GLYCERIN, PROPANEDIOL, DIMETHICONE, OCTOCRYLENE, POTASSIUM CETYL PHOSPHATE, " +
                        "PEG-8 LAURATE, TOCOPHEROL, CAPRYLYL GLYCOL, ACRYLATES/C10-30 ALKYL ACRYLATE CROSSPOLYMER, SODIUM HYDROXIDE, XANTHAN GUM, " +
                        "DISODIUM EDTA, PARFUM / FRAGRANCE.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\t- Lắc đều trước khi sử dụng\n" +
                        "\t\t- Thoa đều lên da trước khi tiếp xúc với ánh nắng ít nhất 20 phút\n" +
                        "\t\t- Thoa lại sau mỗi 2 giờ hoặc sau khi bơi lội, ra mồ hôi\n" +
                        "\t\t- Tránh tiếp xúc trực tiếp với mắt\n", 1));

        // store Olay
        cosmeticList.add(new Cosmetic(1, "Tinh Chất Olay Chống Lão Hóa Chứa Chiết Xuât Retinol24 30ml", "Tinh chất",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic1, null)),
                "Serum OLAY RETINOL24: LIỆU TRÌNH TRẺ HÓA DA BAN ĐÊM, GIÚP MỜ NẾP NHĂN RÕ RỆT SAU 28 NGÀY\n" +
                        "\nRetinol là một dẫn xuất của Vitamin A, có tác dụng trung hòa các gốc tự do, kích thích sản sinh collagen và tái tạo bề mặt da. Retinol giúp làm mờ thâm nám, cải thiện lỗ chân lông và mụn.\n" +
                        "\nOlay Retinol24 kết hợp phức hợp độc quyền giữa Niacinamide, Retinol và Retinyl Propionate, giúp trẻ hóa da hiệu quả mà không gây kích ứng mạnh. Serum giúp mờ nếp nhăn, " +
                        "đều màu da, mờ đốm nâu, và tăng cường độ săn chắc cho da.\n" +
                        "\nCông thức nhẹ nhàng, không chứa hương liệu và phẩm màu, thẩm thấu nhanh, không nhờn dính, đồng thời cấp ẩm cho da suốt 24 giờ.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "- Sử dụng vào ban đêm.\n" +
                        "- Lấy một lượng vừa đủ thoa đều lên mặt và cổ sau khi rửa mặt.\n" +
                        "- Dùng hàng ngày để đạt hiệu quả tối ưu, thấy rõ sự cải thiện sau 28 ngày.", 4));

        cosmeticList.add(new Cosmetic(1, "Kem Dưỡng Ẩm Ban Đêm Sáng Da OLAY LUMINOUS 50G", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic, null)),
                "Cuộc sống bận rộn, năng động, khiến làn da của chúng ta dễ xuất hiện những dấu hiệu lão hóa: da sạm, không đều màu, thâm mụn, nếp nhăn, chảy xệ…\n" +
                        "\nOlay - thương hiệu mỹ phẩm hàng đầu thế giới đến từ Mỹ, với công nghệ hiện đại và thành phần an toàn, giúp cải thiện rõ rệt các vấn đề về da, đồng thời phục hồi và làm chậm quá trình lão hóa.\n" +
                        "\nOlay Luminous sử dụng phức hợp Niacinamide giúp thúc đẩy tái tạo bề mặt da, tăng sinh collagen và làm sáng đều màu da. Kết cấu kem mỏng nhẹ, thấm nhanh, nuôi dưỡng da từ sâu bên trong.\n" +
                        "\nHướng dẫn sử dụng: Lấy một lượng vừa đủ thoa đều lên mặt và cổ, massage nhẹ nhàng. Dùng hàng ngày vào buổi tối sau khi rửa mặt.\n" +
                        "\nXuất xứ thương hiệu: Mỹ\n" +
                        "\nSản xuất tại: Thái Lan", 4));

        cosmeticList.add(new Cosmetic(1, "Bông Tẩy Trang OLAY Vitamin C Sạch Sâu 80 miếng/hộp", "Tẩy trang",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic2, null)),
                "Thiết kế theo Olay\n" +
                        "\nKích thước: 6cm x 6cm\n" +
                        "Số lượng: 80 miếng/hộp\n" +
                        "Thành phần: 100% bông xơ tự nhiên\n" +
                        "Hạn sử dụng: 3 năm kể từ ngày sản xuất\n" +
                        "\nĐặc điểm:\n" +
                        "- Bề mặt dập nổi hình tròn tăng khả năng loại bỏ bụi bẩn giúp vệ sinh da sạch hơn.\n" +
                        "- 100% bông xơ tự nhiên mềm mại, an toàn, có khả năng tự hủy, thân thiện với môi trường.\n" +
                        "- Công nghệ Spunlace xử lý màng bông bằng tia nước áp lực cao giúp bông mịn, dai, không bị xơ trên bề mặt.\n" +
                        "- Đạt chứng nhận ISO 9001, ISO 13485, Hàng Việt Nam chất lượng cao, và các chứng nhận CE.", 4));

        cosmeticList.add(new Cosmetic(1, "Sữa Rửa Mặt Ngày & Đêm", "Sữa rửa mặt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic3, null)),
                "OLAY Total Effects: Phục Hồi & Làm Chậm 7 Dấu Hiệu Lão Hóa\n" +
                        "\nOLAY Total Effects là thương hiệu chăm sóc da đại chúng đầu tiên giới thiệu thành phần Niacinamide (Vitamin B3), được chứng minh giúp tăng cường hàng rào bảo vệ da và phục hồi vẻ ngoài tươi trẻ hơn.\n" +
                        "\nKem dưỡng OLAY Total Effects được cải tiến với 50% Vitamin E kết hợp cùng hệ dưỡng chất vitamin C, B5 và không chứa dầu, giúp phục hồi và làm chậm 7 dấu hiệu lão hóa da:\n" +
                        "- Rãnh và nếp nhăn\n" +
                        "- Da không đều màu\n" +
                        "- Đốm đồi mồi\n" +
                        "- Da kém săn chắc\n" +
                        "- Da khô\n" +
                        "- Da xỉn màu\n" +
                        "- Lỗ chân lông to.", 4));

        cosmeticList.add(new Cosmetic(1, "Combo 2: Serum Căng Mướt, Sáng Khỏe & Phục Hồi Dấu Hiệu Lão", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic4, null)),
                "OLAY Regenerist: Dưỡng Da Căng Mướt, Sáng Khỏe & Phục Hồi Dấu Hiệu Lão Hóa\n" +
                        "\nThành phần chính là Bioavailable Niacinamide, tính ổn định cao và khả năng thẩm thấu sâu vào 10 lớp biểu bì, giúp thúc đẩy quá trình thay mới tế bào da và ngăn chặn dịch chuyển của hắc sắc tố, giúp da tươi sáng, rạng rỡ.\n" +
                        "\nKết hợp cùng hệ dưỡng chất cao cấp:\n" +
                        "- Amino-Peptide (Pro-Collagen) giúp làm mờ nếp nhăn và săn chắc da\n" +
                        "- Axit Hyaluronic (HA) giúp cấp ẩm mạnh mẽ\n" +
                        "- Chiết xuất Carob hỗ trợ phục hồi tổn thương bề mặt da.", 4));

        cosmeticList.add(new Cosmetic(1, "Super Serum OLAY Luminous Niacinamide & Vitamin C ", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic5, null)),
                "Super Serum OLAY Luminous Niacinamide + Vitamin C: Giúp Giảm 5 Năm Vết Thâm, Đốm Nâu Dài Dẳng\n" +
                        "\nBạn có biết, những vết thâm, đốm nâu chỉ là bề nổi của tảng băng, được hình thành tích tụ dưới lớp biểu bì qua năm tháng?\n" +
                        "\nRa mắt dòng sản phẩm OLAY Luminous Niacinamide + Vitamin C MỚI! Với phức hợp Bioavailable^ Vitamin C gồm Niacinamide & Vitamin C, sản phẩm không chỉ tác động trên bề mặt mà còn thẩm thấu sâu vào 10 lớp biểu bì, giúp GIẢM 5 NĂM vết thâm, đốm nâu tích tụ lâu ngày, làm đều màu da cho làn da tươi sáng, rạng rỡ gấp 2 lần**.\n" +
                        "\nSUPER SERUM mỏng nhẹ, thấm nhanh, không nhờn dính, kết hợp với Kem Dưỡng OLAY Luminous Vitamin C để đạt được hiệu quả tối ưu.\n" +
                        "\nKẾT QUẢ NGHIÊN CỨU LÂM SÀNG CHO THẤY^^:\n" +
                        "- 96% người dùng cảm thấy da được cấp ẩm sau khi sử dụng\n" +
                        "- 91% người dùng cảm thấy da sáng khỏe hơn sau khi sử dụng.", 4));

        cosmeticList.add(new Cosmetic(1, "Kem Dưỡng Ẩm Ban Ngày Phục Hồi & Làm Chậm 7 Dấu Hiệu Lão Hóa ", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic6, null)),
                "OLAY Total Effects: Phục Hồi & Làm Chậm 7 Dấu Hiệu Lão Hóa\n" +
                        "\nOLAY Total Effects là thương hiệu chăm sóc da đại chúng đầu tiên giới thiệu thành phần làm chậm dấu hiệu lão hóa mạnh mẽ, Niacinamide (Vitamin B3), giúp tăng cường hàng rào bảo vệ da và phục hồi vẻ ngoài tươi trẻ.\n" +
                        "\nKem dưỡng OLAY Total Effects được cải tiến bổ sung 50% Vitamin E cùng hệ dưỡng chất vitamin C, B5 và không chứa dầu, giúp phục hồi và làm chậm rõ rệt 7 dấu hiệu lão hóa của da:\n" +
                        "- Rãnh và nếp nhăn\n" +
                        "- Da không đều màu\n" +
                        "- Đốm đồi mồi\n" +
                        "- Da kém săn chắc\n" +
                        "- Da khô\n" +
                        "- Da xỉn màu\n" +
                        "- Lỗ chân lông to.", 4));

        // store neutro
        cosmeticList.add(new Cosmetic(1, "Tinh chất cấp nước phục hồi da Neutrogena Serum dưỡng ẩm", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic, null)),
                "Tinh Chất Cấp Nước Phục Hồi Da Neutrogena® Hydro Boost Hyaluronic Acid Serum dưỡng ẩm\n" +
                        "\nƯU ĐIỂM:\n" +
                        "Cấp ẩm ngay lập tức, bổ sung và làm mịn da khô bằng cách truyền hydrat hóa không trọng lượng vào sâu bên trong bề mặt da. Huyết thanh dưỡng ẩm được chứng minh là có khả năng làm mịn và cấp nước cho da để có làn da khỏe mạnh hơn trong suốt 24 giờ.\n" +
                        "\nSản phẩm chứa Axit Hyaluronic tinh khiết + Chất tăng cường độ ẩm, pro-vitamin B5 và glycerin, chất dưỡng ẩm tự nhiên, giúp làm dịu và mềm da, trẻ hóa da và giải quyết các vấn đề như khô, ngứa, mụn và ửng đỏ.\n" +
                        "\nĐược cho là có tác dụng với mọi loại da, kể cả da nhạy cảm, serum nên được sử dụng cả sáng và tối để tạo cảm giác \"sảng khoái\" trên da.\n" +
                        "\nĐã được chứng minh lâm sàng về khả năng tăng cường hydrat hóa không trọng lượng trong 24 giờ cho làn da khỏe mạnh, sáng mịn. Hơn 78% người dùng đồng ý rằng huyết thanh này không để lại cảm giác nhờn trên da.\n" +
                        "\nKhông chứa hương liệu, phù hợp với mọi loại da và da nhạy cảm đã được phê duyệt.", 3));

        cosmeticList.add(new Cosmetic(1, "Nước Hoa Hồng Neutrogena Clear Pore Oil-Eliminating Astringent Toner", "Nước hoa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic1, null)),
                "Nước Hoa Hồng Neutrogena Clear Pore Oil-Eliminating Astringent Toner\n" +
                        "\nDung tích: 236ml\n" +
                        "Xuất xứ: Mỹ\n" +
                        "\n– Công thức có chứa Salicylic Acid, một chất hóa học giúp ngăn ngừa tắc nghẽn và mụn trứng cá, được các bác sĩ da liễu khuyên dùng.\n" +
                        "\n– Toner hỗ trợ ngăn ngừa mụn trứng cá, giảm viêm sưng, hình thành mụn và mụn đầu đen.\n" +
                        "\n– Với độ pH cân bằng, toner không gây kích ứng hay khô da, se khít lỗ chân lông, mang đến làn da sạch sẽ, thoải mái và mịn màng.\n" +
                        "\n– Thiết kế chai chắc chắn, sạch sẽ, tạo cảm giác dễ chịu.\n" +
                        "\n– Sản phẩm thích hợp cho da dầu, da hỗn hợp.\n" +
                        "\nHƯỚNG DẪN SỬ DỤNG:\n" +
                        "- Làm sạch da trước khi áp dụng sản phẩm, thoa nhẹ một lớp mỏng lên bề mặt da.\n" +
                        "- Nếu xảy ra tình trạng khô hoặc bong tróc khó chịu, hãy giảm tần suất sử dụng xuống một lần/ngày hoặc cách ngày.", 3));

        cosmeticList.add(new Cosmetic(1, "Chấm mụn thần thánh Stubborn Acne™ Spot Drying Lotion", "Chấm mụn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic2, null)),
                "Chấm mụn Stubborn Acne™ Spot Drying Lotion\n" +
                        "\nSản phẩm giúp làm khô bề mặt, thu gom cồi mụn nhanh chóng, mụn trông rõ ràng hơn, nhỏ hơn chỉ sau 1 ngày.\n" +
                        "\n- Có tác dụng hiệu quả đối với mụn trứng cá.\n" +
                        "- Làm mờ vết thâm đen sau mụn.\n" +
                        "- Không mùi, rất dịu nhẹ, có thể sử dụng cả ngày.", 3));

        cosmeticList.add(new Cosmetic(1, "Kem chống nắng Sport Face Oil-Free Lotion Sunscreen SPF 70+", "Kem chống nắng",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic3, null)),
                "Kem chống nắng Sport Face Oil-Free Lotion Sunscreen Broad Spectrum SPF 70+\n" +
                        "\nCông thức kem chống nắng dành cho da mặt thể thao của chúng tôi khai thác sức mạnh của Công nghệ Helioplex® để cung cấp khả năng chống " +
                        "tia UVA/UVB phổ rộng vượt trội khỏi ánh nắng mặt trời. Nó cũng chống nước (lên đến 80 phút), cọ xát, mồ hôi và lau để bạn có thể tiếp tục hoạt động mà không lo bị cháy nắng.\n" +
                        "\n- Non-comedogenic (không làm tắc nghẽn lỗ chân lông)\n" +
                        "- Không chứa dầu\n" +
                        "- Không chứa PABA\n" +
                        "- Chống mồ hôi/chống nước (80 phút)\n" +
                        "\nCÁCH SỬ DỤNG\n" +
                        "- Bôi Kem Chống Nắng Neutrogena Sport Face SPF 70 trước 30 phút khi đi ra đường để kem kịp thấm vào da và phát huy tác dụng chống nắng một cách tối đa.\n" +
                        "- Chỉ cần bôi một lớp kem mỏng. Bôi quá nhiều vừa gây lãng phí vừa không tốt cho da vì lớp kem thừa không kịp thấm vào da sẽ có thể là nguyên nhân gây nên bệnh dị ứng da trong mùa hè.", 3));

        cosmeticList.add(new Cosmetic(1, "Kem dưỡng ẩm No7 Protect & Perfect Intense Advanced không mùi", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic4, null)),
                "Kem dưỡng ẩm No7 Protect & Perfect Intense Advanced không mùi - Hàng Mỹ\n" +
                        "\n- Kem dưỡng ban ngày Protect & Perfect Advanced Day Cream: Kem dưỡng ban ngày Protect And Perfect Advanced Day Cream có chỉ số chống nắng phổ rộng SPF 30 để bảo vệ làn da của bạn khỏi các tia có hại của mặt trời.\n" +
                        "\n- Kem dưỡng ban đêm cao cấp Protect & Perfect: Kem dưỡng ban đêm cao cấp Protect And Perfect giúp giảm nếp nhăn và giảm rõ rệt các nếp nhăn. Dễ dàng áp dụng trước khi đi ngủ.\n" +
                        "\nKem cao cấp Protect & Perfect: Cả Kem ban ngày và Kem ban đêm đều có công nghệ tiên tiến bảo vệ và hoàn hảo giúp giảm nếp nhăn và giảm rõ rệt.", 3));

        cosmeticList.add(new Cosmetic(1, "Kem dưỡng cấp ẩm cho mặt La.Roch.e.P.osay", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic5, null)),
                "Kem dưỡng cấp ẩm cho mặt La Roche-Posay Toleriane Double Repair - Dung tích 100ml\n" +
                        "\nThành phần Kem dưỡng Toleriane Double Repair Face Moisturizer UV gồm nước khoáng La Roche-Posay prebiotic, ceramide-3, niacinamide và glycerin, cung cấp độ ẩm lên đến 48 giờ và phục hồi hàng rào bảo vệ tự nhiên của da, phục hồi làn da khỏe mạnh. Ngay lập tức làm dịu ngay cả làn da nhạy cảm với kết cấu kem nhẹ.\n" +
                        "\nKết cấu kem nhẹ của nó dễ dàng hấp thụ vào da để mang lại sự thoải mái ngay lập tức.\n" +
                        "\nPhù hợp với mọi loại da, kể cả da nhạy cảm. Không mùi, không paraben, không dầu, không cồn, không gây mụn.", 3));

        cosmeticList.add(new Cosmetic(1, "Kem dưỡng ẩm ngày / đêm cho da nhạy cảm CETAPHIL REDNESS", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.neutro_cosmetic6, null)),
                "Kem dưỡng ẩm ngày / đêm cho da nhạy cảm CETAPHIL REDNESS RELIEVING MOISTURIZER - Dung tích 50ml - Hàng Mỹ\n" +
                        "\nDành cho da dễ mẩn đỏ, nhạy cảm.\n" +
                        "- Cung cấp độ ẩm liên tục để cải thiện kết cấu da bằng cách bù nước cho da khô, da nhạy cảm và giảm rõ rệt sự xuất hiện của mẩn đỏ do khô.\n" +
                        "- Công thức đặc biệt với Allantoin, chiết xuất cam thảo và caffein để làm dịu mẩn đỏ.\n" +
                        "- Cho làn da cảm giác dịu nhẹ và cân bằng.\n" +
                        "- Bác sĩ da liễu đã thử nghiệm và chứng minh lâm sàng là nhẹ nhàng trên da nhạy cảm.", 3));

        // store thebodyshop
        cosmeticList.add(new Cosmetic(1, "Sữa Rửa Mặt ", "Sữa Rửa Mặt ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the, null)),
                "oại bỏ tạp chất và bụi bẩn hiệu quả cùng Himalayan Charcoal Purifying Clay Wash 100% thuần chay, được chiết xuất từ than tre ở chân núi Himalayan kết hợp với đất sét cao lanh và dầu cây trà theo chương trình Thương Mại Cộng Đồng từ Kenya. \n" +
                        "\n" +
                        " Sữa rửa mặt tạo bọt có kết cấu đất sét mềm mượt, đủ dịu nhẹ cho mọi loại da, mang lại cho làn da cảm giác tươi mát, mềm mại mà không gây cảm giác khô ráp.\n" +
                        "\n" +
                        "\t\t+ Bánh canh cua: Với những miếng thịt cua đỏ tươi được xếp ở bên trên, khi ăn bạn sẽ cảm nhận được sự dai ngọt của thịt cua3.\n" +
                        "\t\t+ Làm sạch và thanh lọc da và giúp thông thoáng lỗ chân lông.\n" +
                        "\t\t+ Loại bỏ dầu thừa và làm mờ vết thâm.\n" +
                        "\t\t+ Làm giàu dưỡng chất với than tre từ Himalayan, đất sét cao lanh, và dầu cây trà trong chương trình Thương Mại Cộng Đồng.\n" +
                        "\t\t+ Đã được kiểm nghiệm da liễu\n", 6));

        cosmeticList.add(new Cosmetic(1, "Bơ Tẩy Trang ", "Tẩy Trang ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the1, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Làn da của bạn sẽ trở nên tươi tắn và hồng hào hơn nhờ loại bơ tẩy trang dưỡng da mới có tông màu má hồng tuyệt đẹp.\n" +
                        "\t\t- Công thức bơ tẩy trang Camomile phiên bản giới hạn mới nhất của chúng tôi được làm giàu với dầu Rose de Mai quý giá từ Grasse, Pháp, sở hữu khả năng hòa tan lớp trang điểm trong vòng chưa đầy 30 giây. Nó phù hợp cho người đeo kính áp tròng và mọi loại da - thậm chí nhạy cảm. Để lại khuôn mặt của bạn cảm giác tươi mát và mềm mại như cánh hoa.\n" +
                        "\t\t- Mùi hương hoa hồng ngọt ngào của sản phẩm không chỉ giúp bạn thư giãn sau một ngày dài mệt mỏi mà nó còn giúp cuốn trôi đi lớp make-up nặng nề trên da mặt.\n" +
                        "\t\t- Hãy sở hữu loại sữa rửa mặt phiên bản giới hạn này bởi vì giống như mùa Rose de Mai, nó cực kỳ giới hạn về số lượng!\n" +
                        "\t\t+ Bơ tẩy trang phiên bản giới hạn\n" +
                        "\t\t+ Hương thơm hoa hồng Rose de Mai\n" +
                        "\t\t+ Thích hợp cho da nhạy cảm và người đeo kính áp tròng\n" +
                        "\t\t+ Được pha với dầu Rose de Mai từ Grasse, Pháp và dầu hoa cúc từ Norfolk, Anh\n" +
                        "\t\t+ Tẩy sạch những lớp trang điểm cứng đầu nhất, kể cả lớp trang điểm không thấm nước\n" +
                        "\t\t+ Làn da được nuôi dưỡng và mềm mượt\n" +
                        "\t\t+ Được chứng nhận bởi Hiệp hội thuần chay\n" +
                        "\t\t+ Đã được kiểm nghiệm da liễu\n", 6));

        cosmeticList.add(new Cosmetic(1, "Kem Chống Nắng", "Kem chống nắng ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the2, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Kem chống nắng Skin Defence Multi-Protection Light Essence sẽ đáp ứng nhu cầu bảo vệ da hàng ngày của bạn - hãy hứa với chúng tôi rằng bạn sẽ không rời khỏi nhà mà chưa có nó.\n" +
                        "\t\t- Tinh chất Skin Defence mới của chúng tôi là một công thức dưỡng ẩm siêu nhẹ, giúp bảo vệ làn da của bạn với độ bảo vệ SPF 50 PA +++. Tinh chất siêu lỏng này sử dụng các bộ lọc UVA/UVB phổ rộng được tối ưu hóa một cách thông minh để giúp bảo vệ da hàng ngày chống lại các tia UV gây hại, cũng như giúp bảo vệ khỏi ô nhiễm trong nhà và ngoài trời.\n" +
                        "\t\t- Công thức hấp thụ nhanh, tạo cảm giác thoáng khí và thoải mái trên da. Sản phẩm không gây nhờn dính, không để lại vệt trắng trên da bạn. Da trông rạng rỡ hơn, đều màu và sẵn sàng để trang điểm.\n" +
                        "\t\t+ Tinh chất dưỡng ẩm thoáng nhẹ, thẩm thấu nhanh\n" +
                        "\t\t+ SPF 50 PA+++ với khả năng chống ô nhiễm trong nhà và ngoài trời\n" +
                        "\t\t+ Bảo vệ da khỏi tia UVA / UVB\n" +
                        "\t\t+ Không để lại cảm giác dính hoặc nhờn trên da\n" +
                        "\t\t+ Lớp lót hoàn hảo trước khi trang điểm\n" +
                        "\t\t+ Thích hợp cho da nhạy cảm\n" +
                        "\t\t+ Được chứng nhận bởi Hiệp hội thuần chay\n" +
                        "\t\t+ Không gây mụn\n", 6));

        cosmeticList.add(new Cosmetic(1, "Kem Dưỡng Ẩm", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the3, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Kem giữ ẩm dạng gel Seaweed Oil-Control Gel Cream giữ lại hơi nước, không dầu, không bịt kín chân lông, và thẩm thấu dễ dàng.\n" +
                        "\t\t- Kem kiểm soát bã nhờn và độ bóng của da trong khi cung cấp độ ẩm cần thiết cho các vùng da có nhu cầu nhất, cân bằng da suốt cả ngày. Cho làn da tươi khỏe, sạch, mịn, và mềm mại, không bị bóng do dầu nhờn.\n" +
                        "\t\t+ Phù hợp cho da dầu hoặc hỗn hợp thiên dầu\n" +
                        "\t\t+ ảo biển giàu khoáng chất từ vịnh Roaring Water, Ireland\n" +
                        "\t\t+ Cấu tạo dịu nhẹ\n" +
                        "\t\t+ Cân bằng dầu và khô thoáng\n", 6));

        cosmeticList.add(new Cosmetic(1, "Bông Tẩy Trang", "Bông Tẩy Trang",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the4, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Bông Tẩy Trang hữu cơ của The Body Shop hoàn chỉnh với hai bề mặt không có lông tơ đáp ứng mọi nhu cầu làm đẹp của bạn.\n" +
                        "\t\t- Một sản phẩm thiết yếu cần phải có trong mọi phòng tắm. Cho làn da tươi khỏe, sạch, mịn, và mềm mại, không bị bóng do dầu nhờn.\n" +
                        "\t\t+ Hữu cơ\n" +
                        "\t\t+ Hai mặt\n" +
                        "\t\t+ Sử dụng với sữa rửa mặt hoặc toner\n" +
                        "\t\t+ Hai bề mặt có kết cấu khác nhau cho kem dưỡng da hoặc chất lỏng\n", 6));

        cosmeticList.add(new Cosmetic(1, "Tẩy Tế Bào Chết ", "Tẩy Tế Bào Chết ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the5, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Nhẹ nhàng xoa dịu cơ thể xinh đẹp trở nên mềm mại như lụa mà không để lại cảm giác da bị mất đi lớp dầu tự nhiên với sản phẩm Tẩy tế bào chết toàn thân Avocado của chúng tôi.\n" +
                        "\t\t- Với 96% thành phần có nguồn gốc tự nhiên, bao gồm cả dầu bơ Hass có nguồn gốc bền vững từ Nam Phi, sản phẩm tẩy tế bào chết dạng kem và giàu dưỡng chất của chúng tôi sẽ loại bỏ các tế bào da chết mang lại cảm giác da mềm mại và mịn màng hơn.\n" +
                        "\t\t- Sản phẩm tẩy da chết có mùi tươi mát này hiện được đóng gói trong bao bì có thể tái chế.\n" +
                        "\n\n Điểm đặc biệt:\n" +
                        "\t\t+ Tẩy tế bào chết ngậm nước cho cơ thể\n" +
                        "\t\t+ Hoàn hảo cho da khô\n" +
                        "\t\t+ Để lại làn da cảm giác mềm mại và mịn màng hơn\n" +
                        "\t\t+ Mùi thơm tinh tế tươi mát và kem\n" +
                        "\t\t+ Được chứng nhận bởi Hiệp hội thuần chay\n", 6));

        cosmeticList.add(new Cosmetic(1, "Lăn Khử Mùi", "Lăn Khử Mùi",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sec_the6, null)),
                "Cảm nhận hương thơm thơm mát với Maca Root & Aloe Fresh Kick Deodorant được chiết xuất từ rễ cây maca, dầu hạt cộng đồng Brazil và dầu hữu cơ trong chương trình Thương Mại Cộng Đồng.\n" +
                        "\n" +
                        "\t\t+ Thanh khử mùi\n" +
                        "\t\t+ Làm mới mùi hương\n" +
                        "\t\t+ Chiết xuất từ rễ cây maca và dầu hạt từ Brazil, lô hội hữu cơ trong chương trình Thương mại Cộng đồng từ Mexico\n", 6));

        // store
        cosmeticList.add(new Cosmetic(1, "Kem dưỡng Vichy", "Dưỡng ẩm",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_vichy1, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER, GLYCERIN, SODIUM PCA, CAPRYLIC/CAPRIC TRIGLYCERIDE, " +
                        "MANNITOL, SODIUM HYALURONATE, CITRIC ACID, TOCOPHEROL.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\t- Thoa lên da mặt và cổ mỗi sáng và tối\n" +
                        "\t\t- Dùng sau khi làm sạch da\n" +
                        "\t\t- Massage nhẹ nhàng để dưỡng chất thẩm thấu\n", 2));

        cosmeticList.add(new Cosmetic(1, "Kem chống nắng Vichy", "Kem chống nắng",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_vichy2, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER, DIISOPROPYL SEBACATE, ETHYLHEXYL SALICYLATE, " +
                        "GLYCERIN, TITANIUM DIOXIDE, BUTYL METHOXYDIBENZOYLMETHANE, SILICA.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\t- Thoa kem trước khi ra ngoài nắng 15-20 phút\n" +
                        "\t\t- Thoa lại sau mỗi 2 giờ hoặc sau khi bơi, ra mồ hôi\n" +
                        "\t\t- Sử dụng hàng ngày cho mặt và cơ thể\n", 2));

        cosmeticList.add(new Cosmetic(1, "Nước khoáng dưỡng da Vichy", "Nước khoáng",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_vichy3, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER, MINERAL SALTS, CALCIUM, MAGNESIUM, BICARBONATES, SILICA.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\t- Xịt đều lên da mặt từ khoảng cách 20cm\n" +
                        "\t\t- Để sản phẩm thẩm thấu tự nhiên hoặc vỗ nhẹ\n" +
                        "\t\t- Dùng bất cứ lúc nào trong ngày để làm dịu và cấp ẩm cho da\n", 2));

        cosmeticList.add(new Cosmetic(1, "Serum Vichy Liftactiv Supreme", "Serum",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cos_vichy4, null)),
                "Thành phần:\n" +
                        "\t\tAQUA/WATER, GLYCERIN, DIMETHICONE, ASCORBIC ACID (VITAMIN C), " +
                        "SODIUM HYALURONATE, ADENOSINE, TOCOPHEROL.\n" +
                        "\nHướng dẫn sử dụng:\n" +
                        "\t\t- Thoa serum lên mặt vào buổi sáng và tối\n" +
                        "\t\t- Sử dụng trước bước kem dưỡng ẩm\n" +
                        "\t\t- Massage nhẹ nhàng để serum thẩm thấu hoàn toàn\n", 2));

        // region cosmeticSize
        cosmeticSizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 55; i++) {
            cosmeticSizeList.add(new CosmeticSize(i, 1, (random.nextInt(50) + 10) * 1000d));
            cosmeticSizeList.add(new CosmeticSize(i, 2, (random.nextInt(50) + 15) * 1000d));
            cosmeticSizeList.add(new CosmeticSize(i, 3, (random.nextInt(50) + 20) * 1000d));
        }

        // region cosmeticSaved
        cosmeticSavedList = new ArrayList<>();
        cosmeticSavedList.add(new CosmeticSaved(1, 3, 1));
        cosmeticSavedList.add(new CosmeticSaved(2, 3, 2));
        cosmeticSavedList.add(new CosmeticSaved(3, 3, 2));
        cosmeticSavedList.add(new CosmeticSaved(4, 3, 2));
        cosmeticSavedList.add(new CosmeticSaved(5, 3, 1));
        cosmeticSavedList.add(new CosmeticSaved(6, 1, 4));
        cosmeticSavedList.add(new CosmeticSaved(7, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(8, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(9, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(10, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(11, 1, 4));

        // region notify
        notifyList = new ArrayList<>();
        notifyList.add(new Notify(1, "Chào bạn mới!",
                "Chào mừng bạn đến với ứng dụng Ứng dụng Đặt Mỹ Phẩm!", "24/10/2024"));
        notifyList.add(new Notify(2, "Thông báo chung!",
                "Ứng dụng Đặt Mỹ Phẩm dùng cho việc lựa chọn mỹ phẩm uy tín chất lượng nhanh chóng.", "24/10/2024"));
        notifyList.add(new Notify(3, "Bạn đi đâu đấy!",
                "Ứng dụng Đặt Mỹ Phẩm luôn luôn chào đón bạn trải nghiệm.", "24/10/2024"));
        notifyList.add(new Notify(4, "Người quản lý app!",
                "Nguyễn Văn Long - Trần Gia Bảo - Tưởng Văn Huế - Vương Đức Cường.", "24/10/2024"));

        // region notify to user
        notifyToUsers = new ArrayList<>();
        notifyToUsers.add(new NotifyToUser(3, 1));
        notifyToUsers.add(new NotifyToUser(3, 2));
        notifyToUsers.add(new NotifyToUser(3, 3));
        notifyToUsers.add(new NotifyToUser(3, 4));

        // region Order
        orderList = new ArrayList<>();
        orderList.add(new Order(1, 1, "Nam Từ Liêm", "4/3/2023", 0d, "Delivered"));
        orderList.add(new Order(2, 1, "Nam Từ Liêm", "5/3/2023", 0d, "Craft"));
        orderList.add(new Order(3, 3, "Triều Khúc", "4/3/2023", 0d, "Coming"));
        orderList.add(new Order(4, 4, "Nam Từ Liêm", "5/4/2023", 0d, "Craft"));
        orderList.add(new Order(5, 1, "Hà Đông", "4/5/2023", 0d, "Coming"));

        // region Order detail
        orderDetailList = new ArrayList<>();
        orderDetailList.add(new OrderDetail(1, 1, 2, 12000d, 1));
        orderDetailList.add(new OrderDetail(1, 2, 3, 15000d, 2));
        orderDetailList.add(new OrderDetail(2, 31, 2, 18000d, 1));
        orderDetailList.add(new OrderDetail(3, 3, 3, 25000d, 3));
        orderDetailList.add(new OrderDetail(3, 4, 3, 25000d, 1));
        orderDetailList.add(new OrderDetail(3, 25, 2, 18000d, 1));
        orderDetailList.add(new OrderDetail(4, 23, 2, 18000d, 2));
        orderDetailList.add(new OrderDetail(4, 32, 3, 25000d, 3));
        orderDetailList.add(new OrderDetail(5, 11, 2, 12000d, 1));
        orderDetailList.add(new OrderDetail(5, 17, 3, 15000d, 2));
        orderDetailList.add(new OrderDetail(5, 31, 2, 18000d, 1));
        orderDetailList.add(new OrderDetail(5, 33, 3, 25000d, 3));
        orderDetailList.add(new OrderDetail(5, 41, 3, 25000d, 1));
    }

    // Insert record into DB
    private void addSampleData(SQLiteDatabase db) {
        SampleData();

        // Add user
        for (User user : userList) {
            db.execSQL(format("INSERT INTO tblUser VALUES(null, '%s','%s', '%s', '%s', '%s', '%s')",
                    user.getName(), user.getGender(), user.getDateOfBirth(), user.getPhone(), user.getUsername(), user.getPassword()));
        }

        // Add store
        for (Store store : storeList) {
            String sql = "INSERT INTO tblStore VALUES(null, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, store.getName());
            statement.bindString(2, store.getAddress());
            statement.bindString(3, store.getPhone());
            statement.bindBlob(4, store.getImage());
            statement.executeInsert();
        }

        // Add store saved
        for (StoreSaved storeSaved : storeSavedList) {
            db.execSQL("INSERT INTO tblstoreSaved VALUES(" + storeSaved.getStoreId() + ", " + storeSaved.getUserId() + ")");
        }

        // Add cosmetic
        for (Cosmetic cosmetic : cosmeticList) {
            String sql = "INSERT INTO tblCosmetic VALUES (null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, cosmetic.getName());
            statement.bindString(2, cosmetic.getType());
            statement.bindBlob(3, cosmetic.getImage());
            statement.bindString(4, cosmetic.getDescription());
            statement.bindLong(5, cosmetic.getStoreId());
            statement.executeInsert();
        }

        // Add cosmetic size
        for (CosmeticSize cosmeticSize : cosmeticSizeList) {
            String sql = "INSERT INTO tblCosmeticSize VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, cosmeticSize.getCosmeticId());
            statement.bindLong(2, cosmeticSize.getSize());
            statement.bindDouble(3, cosmeticSize.getPrice());
            statement.executeInsert();
        }

        // Add cosmetic saved
        for (CosmeticSaved cosmeticSaved : cosmeticSavedList) {
            String sql = "INSERT INTO tblCosmeticSaved VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, cosmeticSaved.getCosmeticId());
            statement.bindLong(2, cosmeticSaved.getSize());
            statement.bindLong(3, cosmeticSaved.getUserId());
            statement.executeInsert();
        }

        // Add notify
        for (Notify notify : notifyList) {
            db.execSQL(format("INSERT INTO tblNotify VALUES(null, '%s', '%s', '%s')",
                    notify.getTitle(), notify.getContent(), notify.getDateMake()));
        }

        // Add notify to user
        for (NotifyToUser notifyToUser : notifyToUsers) {
            db.execSQL("INSERT INTO tblNotifyToUser VALUES('"
                    + notifyToUser.getNotifyId() + "', '"
                    + notifyToUser.getUserId() + "')");
        }

        // Add order
        for (Order order : orderList) {
            String sql = "INSERT INTO tblOrder VALUES(null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, order.getUserId());
            statement.bindString(2, order.getAddress());
            statement.bindString(3, order.getDateOfOrder());
            statement.bindDouble(4, order.getTotalValue());
            statement.bindString(5, order.getStatus());
            statement.executeInsert();
        }

        // Add order detail
        for (OrderDetail orderDetail : orderDetailList) {
            db.execSQL("INSERT INTO tblOrderDetail VALUES(" + orderDetail.getOrderId() + ", " +
                    orderDetail.getCosmeticId() + ", " +
                    orderDetail.getSize() + ", " +
                    orderDetail.getPrice() + ", " +
                    orderDetail.getQuantity() + ")");
        }

        // Update order price
        String queryGetTotal;
        for (Order order : orderList) {
            queryGetTotal = "SELECT SUM(price * quantity) FROM tblOrderDetail WHERE order_id=" + order.getId();
            @SuppressLint("Recycle")
            Cursor cursor = db.rawQuery(queryGetTotal, null);
            cursor.moveToFirst();

            String sql = "UPDATE tblOrder SET total_value=" + cursor.getDouble(0) + " WHERE id=" + order.getId();
            db.execSQL(sql);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create table "User"
        String queryCreateUser = "CREATE TABLE IF NOT EXISTS tblUser(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "gender VARCHAR(10)," +
                "date_of_birth VARCHAR(20)," +
                "phone VARCHAR(15)," +
                "username VARCHAR(30)," +
                "password VARCHAR(100))";
        sqLiteDatabase.execSQL(queryCreateUser);

        //Create table "store" => để lưu ảnh trong SQLite ta dùng BLOG (Binary Longer Object)
        String queryCreateStore = "CREATE TABLE IF NOT EXISTS tblStore(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200), " +
                "address NVARCHAR(200)," +
                "phone CHAR(10)," +
                "image BLOB)";
        sqLiteDatabase.execSQL(queryCreateStore);

        //Create table "storeSaved"
        String queryCreateStoreSaved = "CREATE TABLE IF NOT EXISTS tblStoreSaved(" +
                "store_id INTEGER, user_id INTEGER," +
                "PRIMARY KEY(store_id, user_id))";
        sqLiteDatabase.execSQL(queryCreateStoreSaved);

        //Create table "Cosmetic"
        String queryCreateCosmetic = "CREATE TABLE IF NOT EXISTS tblCosmetic(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "type NVARCHAR(200)," +
                "image BLOB," +
                "description NVARCHAR(200)," +
                "store_id INTEGER)";
        sqLiteDatabase.execSQL(queryCreateCosmetic);

        //Create table "CosmeticSize"
        String queryCreateCosmeticSize = "CREATE TABLE IF NOT EXISTS tblCosmeticSize(" +
                "cosmetic_id INTEGER," +
                "size INTEGER ," +
                "price DOUBLE," +
                "PRIMARY KEY (cosmetic_id, size))";
        sqLiteDatabase.execSQL(queryCreateCosmeticSize);

        //Create table "CosmeticSaved"
        String queryCreateCosmeticSaved = "CREATE TABLE IF NOT EXISTS tblCosmeticSaved(" +
                "cosmetic_id INTEGER," +
                "size INTEGER ," +
                "user_id INTEGER," +
                "PRIMARY KEY (cosmetic_id, size, user_id))";
        sqLiteDatabase.execSQL(queryCreateCosmeticSaved);

        //Create table "Order"
        String queryCreateOrder = "CREATE TABLE IF NOT EXISTS tblOrder(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "address NVARCHAR(200)," +
                "date_order VARCHAR(20)," +
                "total_value DOUBLE," +
                "status VARCHAR(200))";
        sqLiteDatabase.execSQL(queryCreateOrder);

        //Create table "OrderDetail"
        String queryCreateOrderDetail = "CREATE TABLE IF NOT EXISTS tblOrderDetail(" +
                "order_id INTEGER," +
                "cosmetic_id INTEGER," +
                "size INTEGER," +
                "price DOUBLE," +
                "quantity INTEGER," +
                "PRIMARY KEY (order_id, cosmetic_id, size))";
        sqLiteDatabase.execSQL(queryCreateOrderDetail);

        //Create table "Notify"
        String queryCreateNotify = "CREATE TABLE IF NOT EXISTS tblNotify(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title NVARCHAR(200)," +
                "content NVARCHAR(200)," +
                "date_make VARCHAR(20))";
        sqLiteDatabase.execSQL(queryCreateNotify);

        //Create table "NotifyToUser"
        String queryCreateNotifyToUser = "CREATE TABLE IF NOT EXISTS tblNotifyToUser(" +
                "notify_id INTEGER," +
                "user_id INTEGER," +
                "PRIMARY KEY (notify_id, user_id))";
        sqLiteDatabase.execSQL(queryCreateNotifyToUser);

        Log.i("SQLite", "DATABASE CREATED");
        addSampleData(sqLiteDatabase);
        Log.i("SQLite", "ADDED DATA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("SQLite", "Upgrade SQLite");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblNotifyToUser");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblNotify");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblCosmeticSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblCosmeticSize");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblCosmetic");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrderDetail");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrder");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblStoreSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblStore");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblUser");

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
