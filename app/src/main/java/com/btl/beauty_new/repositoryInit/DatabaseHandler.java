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

    // Convert Image
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

        // Chuyển kiểu
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

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

        // region store saved
        storeSavedList = new ArrayList<>();
        storeSavedList.add(new StoreSaved(1, 3));
        storeSavedList.add(new StoreSaved(4, 3));
        storeSavedList.add(new StoreSaved(1, 1));
        storeSavedList.add(new StoreSaved(1, 2));
        storeSavedList.add(new StoreSaved(2, 2));
        storeSavedList.add(new StoreSaved(6, 3));

        // region cosmetic
        cosmeticList = new ArrayList<>();
        // region Shiseido
        cosmeticList.add(new Cosmetic(1, "Tinh chất dưỡng da", "Dưỡng da",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_daudo, null)),
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
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_suadua, null)),
                "Rạng rỡ tinh tế. " +
                        "\t\t Sữa rửa mặt có chứa Bột trắng siêu mịn và đất sét trắng để loại bỏ các tạp chất một cách hiệu quả. Giàu dưỡng chất. Bọt êm mịn. Loại bỏ các tế bào da xỉn màu ở bề mặt, các chất gây ô nhiễm và các chất bị ôxi hóa gây nên lão hóa. Được làm từ những nguyên liệu có khả năng dưỡng ẩm sâu, không gây khô da, củng cố khả năng bảo vệ từ bên trong.\n" +
                        "\t\t Phù hợp cho mọi loại da.\n" +
                        "\n\n- Cách dùng: sữa rửa mặt\n" +
                        "\t\t + Thêm một it nước, tạo bọt trên sản phẩm"+
                        "\t\t + Massage nhẹ nhàng toàn gương mặt"+
                        "\t\t + Rửa mặt lại thật sạch bằng nước", 5));
        cosmeticList.add(new Cosmetic(1, "Kem dưỡng ban ngày", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_vani, null)),
                "Kem dưỡng cao cấp ban ngày đa chức năng với kết cấu mịn mượt giàu ẩm giúp duy trì cảm giác ẩm mượt tối ưu suốt cả ngày. Công nghệ độc quyền của Shiseido kích thích Nhân tố bảo vệ da ban ngày để mang đến làn da rạng rỡ từ bên trong. Làn da được bảo vệ ngay cả khi bị tác động bởi những yếu tố gây hại như tia UV, môi trường khô, ôxi hóa và sự ô nhiễm. Có thể sử dụng dưới lớp trang điểm.:\n" +
                        "\t\t Bảo vệ da với SPF 20.\n" +
                        "\t\t+ Tổng quan:\n" +
                        "\t\t  VẺ ĐẸP VƯỢT THỜI GIAN\n" +
                        "10 NĂM TRÂN QUÝ VẺ ĐẸP.\n" +
                        "10 NĂM KHÁM PHÁ RA BÁU VẬT CỦA NHẬT BẢN: THẢO DƯỢC ENMEI.\n" +
                        "10 NĂM CỦNG CỐ VẺ ĐẸP VĨNH CỬU, LÀN DA ĐÀN HỒI VÀ TỎA SÁNG RỰC RỠ.\n" +
                        "\t\t Thành phần chính:"+
                        "\t\t  PHỨC HỢP SKINGENECELL ENMEI\n" +
                        "Thảo dược từ xa xưa nay trở thành nguồn năng lượng sống mới cho làn da. Tìm thấy sâu trong rừng thiêng nơi ngọn núi Koya huyền bí, loài thảo mộc đã được các vị thiền sư sử dụng hàng thế kỷ nay. Các nghiên cứu hiện đại đã giúp chạm đến và khai thác sức mạnh chống lão hóa và duy trì vẻ đẹp vượt trội cùa loài thảo mộc huyền bí.\n" , 5));
        cosmeticList.add(new Cosmetic(1, "Kem dưỡng mắt", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem dưỡng mắt chuyên sâu, giảm rõ rệt quầng thâm mắt, cho vẻ ngoài trẻ trung đầy sức sống." +
                        "\n\n  Thành phần chính:\n" +
                        "\t\t+  WATER (AQUA)･DIMETHICONE･BUTYLENE GLYCOL･GLYCERIN･ALCOHOL･DIMETHICONE/VINYL DIMETHICONE CROSSPOLYMER･MYRISTYL MYRISTATE･PETROLATUM･HYDROGENATED POLYDECENE･BEHENYL ALCOHOL･CETYL ETHYLHEXANOATE･GLYCERYL STEARATE SE･POTASSIUM METHOXYSALICYLATE･POLYMETHYL METHACRYLATE･STEARYL ALCOHOL･DIMETHYLACRYLAMIDE/SODIUM ACRYLOYLDIMETHYLTAURATE CROSSPOLYMER･POLYSORBATE 60･PEG-40 STEARATE･TOCOPHERYL ACETATE･PHENOXYETHANOL･TITANIUM DIOXIDE (CI 77891)･FRAGRANCE (PARFUM)･SORBITAN TRISTEARATE･TRISODIUM EDTA･PEG-10 DIMETHICONE･SODIUM CITRATE･ORYZANOL･2-O-ETHYL ASCORBIC ACID･MICA･SILICA･XANTHAN GUM･SODIUM METAPHOSPHATE･LIMONENE･CITRIC ACID･PEG/PPG-14/7 DIMETHYL ETHER･ALUMINUM HYDROXIDE･BENZYL BENZOATE･HYDROXYISOHEXYL 3-CYCLOHEXENE CARBOXALDEHYDE･IRON OXIDES (CI 77491)･SODIUM HYALURONATE･LINALOOL･CRATAEGUS MONOGYNA FLOWER EXTRACT･TOCOPHEROL･\n" +
                        "\n\n- Cách dùng: kem dưỡng mắt\n" +
                        "\t\t + dàn trải một lượng nhỏ sản phẩm quanh vùng mắt."+
                        "\t\t + Thoa đều khắp mi mắt và vùng da dưới mắt."+
                        "\t\t + Massage nhẹ nhàng theo hướng từ gò mà lên.", 5));
        cosmeticList.add(new Cosmetic(1, "Kem chống nắng dưỡng da", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "huyển hóa một phần ánh nắng mặt trời thành ánh sáng sinh học với Công nghệ Sun Dual Care™. " +
                        "\n\n Sản phẩm chống nắng đầu tiên giúp bảo vệ da khỏi tia UV, đồng thời, chuyển hóa một phần ánh sáng mặt trời thành ánh sáng có lợi, tối ưu hiệu quả chăm sóc da.\n" +
                        "\t\t Phù hợp để dùng hằng ngày với kết cấu nhẹ tênh, không dầu, với thành phần các chất chống ôxi hóa và hyaluronic acid, sản phẩm giúp bảo vệ da khỏi các yếu tố ô nhiễm môi trường gây sạm hoặc khô da, để lại một làn da ẩm mịn, tươi sáng.\n" +
                        "\t\t SPF 50.\n" +
                        "\t\t Tinh chất Spirulina Energy Essence\n" +
                        "Chuyển hóa một phần ánh nắng mặt trời thành ánh sáng có lợi\n" +
                        "\t\t Ít bị rửa trôi\n" +
                        "So sánh với các sản phẩm chống nắng\n" +
                        "không kháng nước và công thức kháng nước lưu lại trên da\n" +
                        "\t\t Không gây bết dính", 5));
        cosmeticList.add(new Cosmetic(1, "Mặt nạ ban đêm", "Mặt nạ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Đánh thức giác quan của làn da. Toả sáng rạng rỡ từ bên trong." +
                        "\t\t Ứng dụng khoa học hệ thần kinh của các giác quan trên làn da. Chất gel giàu dưỡng chất, êm ái trên da, áp dụng công nghệ ReNeura Technology+™ gửi các tín hiệu cảm biến bên trong làn da, giúp da phản ứng tối ưu đến hiệu quả chống lão hoá của sản phẩm. \n" +
                        "\t\t Tập trung vào quá trình hoạt động ban đêm của làn da, kem dưỡng cung cấp dưỡng chất tràn đầy, giúp giải quyết các tổn thương xuất hiện ban ngày, ngay trong giấc ngủ của bạn. \n" +
                        "\t\t Làm sáng rõ rệt, loại bỏ đốm nâu, làm đều màu da, và xoá những vết nhăn nhỏ. Giúp bạn toả sáng.\n", 5));

        // region Banh mi
        cosmeticList.add(new Cosmetic(1, "Bánh mì bò kho", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_bokho, null)),
                "Bánh mì bò kho là một món ăn ngon và bổ dưỡng, thường được chế biến cho bữa ăn sáng. Dưới đây là cách nấu bánh mì bò kho1:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 1 kg thịt bắp bò hoặc thịt mông bò\n" +
                        "\t\t+ 2 củ hành khô hoặc ½ củ hành tây\n" +
                        "\t\t+ 1 trái cà chua\n" +
                        "\t\t+ 1 mẩu gừng nhỏ\n" +
                        "\t\t+ 5 tép tỏi\n" +
                        "\t\t+ 3 cây sả\n" +
                        "\t\t+ 2 hoa hồi\n" +
                        "\t\t+ 1 thanh quế\n" +
                        "\t\t+ 2 củ cà rốt\n" +
                        "\t\t+ 1 thìa dầu màu điều\n" +
                        "\t\t+ 1 thìa bột năng\n" +
                        "\t\t+ Nước dừa tươi (nếu có)\n" +
                        "\t\t+ Bánh mì: 2 cái1\n" +
                        "\nCách làm:\n" +
                        "\n" +
                        "\t\t+ Sơ chế thịt bò: Thịt bò vo sạch, sau đó ngâm nước khoảng 5-6 tiếng cho đỗ nở mềm1.\n" +
                        "\t\t+ Nấu thịt bò: Cho thịt vào nồi, sau đó cho nước ngập mặt thịt (cỡ 1 đốt ngón tay). Bật bếp và không cần đậy nắp nồi, nấu cho tới khi thịt chín mềm và nước cũng rút cạn thì tắt bếp1.\n" +
                        "\t\t+ Sơ chế các nguyên liệu khác: Hành tây thái mỏng. Gừng, tỏi, sả đập dập. Cà chua cắt hạt lựu. Cà rốt tỉa hoa, thái miếng vừa ăn. Hoa hồi, quế rang thơm. Hòa tan bột năng cùng một chút nước1.\n" +
                        "\t\t+ Nấu bò kho: Phi thơm gừng, tỏi, sả với 1 thìa dầu màu điều. Cho thịt bò vào xào đến khi săn lại thì cho cà chua vào đảo đều tay. Cho nước dừa, nước lọc xâm xấp mặt thịt. Cho tiếp quế, hoa hồi vào nồi và đun sôi1.\n" +
                        "\t\t+ Sau khoảng 30 phút thì vớt xác quế, hoa hồi, sả ra ngoài1.\n" +
                        "\t\t+ Cho bột năng đã hòa tan vào và khuấy đều1.\n" +
                        "\t\t+ Cuối cùng cho cà rốt, nêm nếm gia vị vừa ăn1.\n" +
                        "\t\t+ Đun sôi thêm một lúc đến khi cà rốt chín thì tắt bếp1.", 1));
        cosmeticList.add(new Cosmetic(1, "Bánh mì bơ tỏi", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_botoi, null)),
                "Bánh mì bơ tỏi là một món ăn rất nhiều người yêu thích, không chỉ tiện lợi cho bữa sáng dinh dưỡng mà còn cực kỳ thích hợp cho những buổi tiệc nhỏ1. \n\n-  Dưới đây là một số công thức nấu bánh mì bơ tỏi:\n" +
                        "\t\t+ Hướng Dẫn Cách Làm Bánh Mì Bơ Tỏi Siêu Ngon - Huongnghiepaau1: Công thức này giới thiệu cách làm bánh mì bơ tỏi kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh mì bơ tỏi giòn thơm nức mũi - Bách hóa XANH2: Công thức này hướng dẫn cách làm bánh mì bơ tỏi thơm ngon, giòn rụm đơn giản tại nhà2.\n" +
                        "\t\t+ Cách làm bánh mì bơ tỏi bằng nồi chiên không dầu hấp dẫn3: Công thức này hướng dẫn cách làm bánh mì bơ tỏi với nồi chiên không dầu, giúp bạn có được chiếc bánh mì giòn rụm, thơm phức3.", 1));
        cosmeticList.add(new Cosmetic(1, "Bánh mì chảo", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_chao, null)),
                "Bánh mì chảo là một món ăn ngon và nhanh gọn, thích hợp cho bữa sáng. Dưới đây là cách làm bánh mì chảo pate, xúc xích, trứng1:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 2 muỗng canh dầu ăn\n" +
                        "\t\t+ 1 muỗng canh tỏi băm\n" +
                        "\t\t+ 2 trái cà chua cắt hạt lựu\n" +
                        "\t\t+ 3 muỗng canh sốt cà chua\n" +
                        "\t\t+ 1 muỗng cà phê muối\n" +
                        "\t\t+ 1 muỗng cà phê đường\n" +
                        "\t\t+ 1 muỗng cà phê tiêu\n" +
                        "\t\t+ Bánh mì, pa tê, xúc xích, trứng gà (số lượng tùy thích)1\n" +
                        "\nCách làm:\n" +
                        "\n" +
                        "\t\t+ Làm sốt cà chua: Cho 1 muỗng canh dầu ăn vào nồi để phi tỏi cho thơm. Khi tỏi đã vàng thì cho cà chua cắt nhỏ và 3 muỗng canh sốt cà chua vào trộn đều, thêm chút nước và nêm với 1 muỗng cà phê muối, 1 muỗng cà phê đường và 1 muỗng cà phê tiêu rồi nấu cho đến khi cà chua nhừ là được1.\n" +
                        "\t\t+ Làm bánh mì chảo: Làm nóng chảo rồi cho 1 muỗng canh dầu ăn vào, dầu ăn nóng thì cho xúc xích đã cắt đôi và 1 viên pa tê vào chiên cho xúc xích vàng. Khi xúc xích đã vàng thì đập trứng gà và hành tây cắt sợi vào. Cuối cùng múc nước sốt đã làm chan vào rồi cho 1 viên bơ lạt lên", 1));
        cosmeticList.add(new Cosmetic(1, "Bánh mì hoa cúc", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_hoacuc, null)),
                "Bánh mì chảo là một món ăn ngon được giới trẻ yêu thích và ngày càng trở nên phổ biến1. Hương vị hòa quyện giữa xúc xích, trứng, pate, thịt bò mềm… đắm chìm trong nước xốt đậm đà, sánh mịn thật khó quên khi đã thưởng thức qua 1 lần1. \n\n- Dưới đây là một số công thức nấu bánh mì chảo:\n" +
                        "\t\t+ Cách làm bánh mì chảo pate, xúc xích1: Công thức này giới thiệu cách làm bánh mì chảo kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Tự Tay Làm Bánh Mì Chảo Cho Siêu Ngon Bữa Sáng Đầy Năng Lượng2: Công thức này hướng dẫn cách làm bánh mì chảo thơm ngon, giòn rụm đơn giản tại nhà2.\n" +
                        "\t\t+ Bánh mì chảo cá hộp3: Bạn tìm 1 món ăn sáng chế biến nhanh nhưng cũng phải ngon miệng thì bánh mì chảo là món bạn đang tìm kiếm. Nguyên liệu dễ mua, thời gian thực hiện nhanh chóng và còn rất được lòng của mọi người3.\n" +
                        "\t\t- 4 cách làm bánh mì chảo tại nhà thơm ngon cho bữa sáng4: Công thức này hướng dẫn cách làm bánh mì chảo theo công thức như thế nào ngon nhất. Vì thế, hôm nay Chef.vn sẽ hướng dẫn các bạn cách nấu bò kho bánh mì rất đơn giản mà lại dễ làm, quan trọng nhất là bạn phải biết mẹo để ướp thịt bò để món bò kho đậm đà thơm ngon mà không bị dai", 1));
        cosmeticList.add(new Cosmetic(1, "Bánh mì hoa cúc", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_hoacuc, null)),
                "Bánh mì hoa cúc, còn được gọi là Brioche, là một loại bánh mì có nguồn gốc từ Pháp. Với thành phần giàu bơ và trứng, bánh mì hoa cúc có một lớp vỏ mềm, ẩm và vàng sẫm. Khi nướng lên, bánh luôn có thớ mềm và xốp, hương vị vô cùng tuyệt vời1. \n\n- Dưới đây là một số công thức nấu bánh mì hoa cúc:\n" +
                        "\t\t+ Cách Làm Bánh Mì Hoa Cúc Đơn Giản & Ngon Nhất1: Công thức này giới thiệu cách làm bánh mì hoa cúc kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh mì hoa cúc Harrys (đơn giản, nhồi bột dễ) [VIDEO]2: Công thức này hướng dẫn cách làm bánh mì hoa cúc theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh mì hoa cúc Harrys (đơn giản, nhồi bột dễ) [VIDEO] - Savoury Days2: Công thức này hướng dẫn cách làm bánh mì hoa cúc theo công thức như thế nào ngon nhất2", 1));
        cosmeticList.add(new Cosmetic(1, "Bánh mì kẹp thịt", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_kepthit, null)),
                "Bánh mì kẹp thịt là một món ăn truyền thống của người Việt, được nhiều người yêu thích và trở thành “siêu sao” ẩm thực không chỉ ở Việt Nam mà còn trên khắp thế giới1. \n\n- Dưới đây là một số công thức nấu bánh mì kẹp thịt:\n" +
                        "\t\t+ Cách Làm Bánh Mì Kẹp Thịt Việt Nam Làm Mê Mẩn Thực Khách Thế Giới - Huongnghiepaau1: Công thức này giới thiệu cách làm bánh mì kẹp thịt kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm 4 món bánh mì kẹp siêu ngon và đơn giản - YummyDay2: Công thức này hướng dẫn cách làm bánh mì kẹp thơm ngon, giòn rụm đơn giản tại nhà2.\n" +
                        "\t\t+ Bánh mì sandwich kẹp thịt nguội cho bữa sáng ngon lành3: Công thức này hướng dẫn cách làm bánh mì sandwich kẹp thịt nguội cho bữa sáng ngon lành3.\n" +
                        "\t\t+ Top 10 loại nhân kẹp bánh mì thơm ngon, dinh dưỡng cho bữa sáng4: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất4.\n" +
                        "\t\t+ Cách làm bánh mì kẹp thịt ngon không kém ngoài hàng5: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất5.", 1));
        cosmeticList.add(new Cosmetic(1, "Bánh mì kẹp xúc xích", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_kepxucxich, null)),
                "Bánh mì kẹp xúc xích là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. \n\n- Dưới đây là một số công thức nấu bánh mì kẹp xúc xích:\n" +
                        "\t\t+ Cách làm bánh mì kẹp trứng xúc xích cho bữa sáng ngon miệng!1: Công thức này giới thiệu cách làm bánh mì kẹp trứng xúc xích kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh mì kẹp trứng xúc xích CỰC NGON2: Công thức này hướng dẫn cách làm bánh mì kẹp trứng xúc xích thơm ngon hấp dẫn chi tiết2.\n" +
                        "\t\t+ Cách làm bánh mì kẹp xúc xích NGON, RẺ, CỰC DỄ LÀM tại nhà3: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất3.\n" +
                        "\t\t+ Bật mí cách làm bánh mì kẹp xúc xích đơn giản, chuẩn vị4: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất4.", 1));
        cosmeticList.add(new Cosmetic(1, "Hamburger bò", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Hamburger bò là một món ăn nhanh phổ biến của người phương Tây1. \n\n- Dưới đây là một số công thức nấu Hamburger bò:\n" +
                        "\t\t+ Cách làm Hamburger bò cực đơn giản chỉ trong 3 bước - PasGo2: Công thức này giới thiệu cách làm Hamburger bò kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu2.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng1: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));
        cosmeticList.add(new Cosmetic(1, "Hamburger heo", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_heo, null)),
                "Dưới đây là cách làm Hamburger Heo Bò Nhà Làm1:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 100g thịt heo xay\n" +
                        "\t\t+ 100g thịt bò xay\n" +
                        "\t\t+ 2 cái vỏ bánh hamburger\n" +
                        "\t\t+ Phô mai con bò cười: 1 viên\n" +
                        "\t\t+ Phô mai lát: 2 lá (có thể dùng bất kì phomai lát nào cũng được)\n" +
                        "\t\t+ Hành tây, hành củ, giấm, xà lách, muối, đường, bột nêm\n" +
                        "\t\t+ Sốt kewpie, sốt tương ớt\n" +
                        "\t\t+ 1 trái cà chua1\n" +
                        "\nCách làm:\n" +
                        "\n" +
                        "\t\t+ Rửa sạch xà lách, cà chua, hành tây.\n" +
                        "\t\t+ Trộn thịt heo và bò, cắt nhỏ hành củ, cắt 1 khoanh hành tây bằm nhỏ. Cho 2 muỗng cafe đường, 1 muỗng cafe muối, 1 muỗng cafe bột nêm. Trộn đều. Phômai con bò cười cắt nhỏ trộn chung với thịt. Chia làm 2 phần thịt bằng nhau vo viên, ép dẹp vừa đủ.\n" +
                        "\t\t+ Cắt 2 khoanh hành tây.\n" +
                        "\t\t+ Bỏ 2 phần thịt vào chảo tráng dầu chiên vàng đều 2 mặt khoảng 10p-15p. Vớt thịt ra cho hành tây vào xào sơ thêm ít đường và muối vào khi xào hành.\n" +
                        "\t\t+ Hành tây đã xào sơ cho vào chén cho 1 muỗng cafe giấm ăn, 2 muỗng cafe đường. Trộn đều.\n" +
                        "\t\t+ Trình bày: cắt vỏ bánh làm đôi. Cho sốt kewpie vào 1 bên vỏ bánh, sau đó cho lên đó 1 lớp rau củ, tiếp đến thêm 1 lớp thịt và 1 muỗng sốt hoisin lên trên, tiếp tục cho 1 lớp nấm lên trên1", 1));
        cosmeticList.add(new Cosmetic(1, "Hamburger bò", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Hamburger bò là một món ăn nhanh phổ biến của người phương Tây1. \n\n- Dưới đây là một số công thức nấu Hamburger bò:\n" +
                        "\t\t+ Cách làm Hamburger bò cực đơn giản chỉ trong 3 bước - PasGo2: Công thức này giới thiệu cách làm Hamburger bò kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu2.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng1: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));
        cosmeticList.add(new Cosmetic(1, "Hamburger heo", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_heo, null)),
                "Hamburger heo là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. \n\n- Dưới đây là một số công thức nấu Hamburger heo:\n" +
                        "\t\t+ Cách làm Hamburger heo cực đơn giản chỉ trong 3 bước - PasGo1: Công thức này giới thiệu cách làm Hamburger heo kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng2: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));
        cosmeticList.add(new Cosmetic(1, "Hamburger gà", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_ga, null)),
                "Hamburger gà là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. \n\n- Dưới đây là một số công thức nấu Hamburger gà:\n" +
                        "\t\t+ Cách làm Hamburger gà cực đơn giản chỉ trong 3 bước - PasGo1: Công thức này giới thiệu cách làm Hamburger gà kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng2: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));

        // region Banh ngot
        cosmeticList.add(new Cosmetic(1, "Bánh đậu xanh cốt dừa", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhdauxanh_cotdua, null)),
                "Bánh đậu xanh cốt dừa là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh đậu xanh cốt dừa:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 200g đậu xanh không vỏ\n" +
                        "\t\t+ 120g đường\n" +
                        "\t\t+ Một chút muối\n" +
                        "\t\t+ 1 thìa dầu ăn\n" +
                        "\t\t+ 60ml nước cốt dừa1\n" +
                        "\nCách làm:\n" +
                        "\n" +
                        "\t\t+ Đậu xanh vo sạch, sau đó ngâm nước 2-3 tiếng cho đỗ nở mềm1.\n" +
                        "\t\t+ Cho đỗ, một chút muối vào nồi, sau đó cho nước ngập mặt đỗ (cỡ 1 đốt ngón tay). Bật bếp và không cần đậy nắp nồi, nấu cho tới khi đỗ chín mềm và nước cũng rút cạn thì tắt bếp1.\n" +
                        "\t\t+ Cho đỗ vào máy xay sinh tố cùng với nước cốt dừa, xay cho hỗn hợp nhuyễn mịn1.\n" +
                        "\t\t+ Đổ hỗn hợp vừa xay ra chảo chống dính và bật bếp nhỏ lửa, cho thêm đường, dầu ăn và trộn đều, xào như vậy cho tới khi bột đậu xanh khô ráo là tắt bếp1.\n" +
                        "\t\t+ Chuẩn bị 1 chiếc khuôn vuông, sau đó lót màng bọc thực phẩm vào rồi lấy 1 lượng đậu xanh vừa đủ, trút vào khuôn và ấn đều 4 góc sao cho phẳng đều là được1.\n" +
                        "\t\t+ Lấy bánh ra khỏi khuôn, bôi 1 lớp mỏng dầu ăn lên dao rồi cắt bánh thành các miếng vuông vừa nhỏ là xong rồi nhé! Những chiếc bánh nhỏ xinh lại bùi thơm vị đỗ xanh, béo ngậy nước cốt dừa, ngọt vừa phải, ăn không bị ngấy1.", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh matcha", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_matcha, null)),
                "Bánh matcha là một loại bánh ngọt với hương vị đặc trưng từ bột trà xanh matcha. Dưới đây là một số cách làm bánh matcha phổ biến:\n" +
                        "\n" +
                        "Bánh bông lan matcha1:\n" +
                        "\n" +
                        "\nNguyên liệu: 100g bột mì, 50g bột bắp, 5g bột nở, 20g bột matcha, 3 quả trứng gà, 80g đường, 40g bơ nhạt, muối, vani1.\n" +
                        "\t\t+ Cách chế biến: Đầu tiên, bạn cần tách lòng đỏ và lòng trắng trứng, sau đó đánh lòng đỏ với 40g đường và vani. Tiếp theo, hòa tan bơ và dầu ăn trong nước sôi. Khi hỗn hợp bơ còn ấm, bạn cho bột trà xanh vào và khuấy đều. Sau cùng, bạn trộn hỗn hợp bột mì, bột bắp và bột nở với nhau và cho vào hỗn hợp trà xanh. Tiếp tục thêm hỗn hợp lòng đỏ trứng ban đầu và trộn thật đều1.\n" +
                        "\t\t+ Bánh mousse matcha1:\n" +
                        "\n" +
                        "\nNguyên liệu: Vài lát bánh bông lan matcha, 250g whipping cream, 5g bột trà xanh, 60g đường, 2 lá gelatine1.\n" +
                        "\t\t+ Cách chế biến: Bạn cần phải kết hợp các nguyên liệu này để tạo ra một lớp mousse mềm mại và thơm ngon1.\n" +
                        "\t\t+ Bánh tiramisu matcha1:\n" +
                        "\n" +
                        "\nNguyên liệu: Các nguyên liệu cần thiết cho loại bánh này gồm có matcha, kem mascarpone, và các loại nguyên liệu khác tùy thuộc vào cách bạn muốn làm1.\n" +
                        "\t\t+ Cách chế biến: Bạn sẽ cần phải kết hợp các nguyên liệu này để tạo ra một lớp tiramisu mềm mại và thơm ngon1.\n" +
                        "\t\t+ Bánh quy matcha1:\n" +
                        "\n" +
                        "\nNguyên liệu: Bạn sẽ cần có bột matcha, bột mì, và các nguyên liệu khác tùy thuộc vào cách bạn muốn làm1.\n" +
                        "\t\t+ Cách chế biến: Bạn sẽ cần phải kết hợp các nguyên liệu này để tạo ra những chiếc bánh quy giòn và thơm ngon1.", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh sầu riêng", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "Bánh matcha là một loại bánh ngọt có hương vị đặc trưng của bột trà xanh matcha. Dưới đây là một số công thức làm bánh matcha:\n" +
                        "\n" +
                        "\t\t+ Bánh bông lan matcha: Bánh bông lan matcha có vị béo ngọt, đắng nhẹ của bột trà xanh và hương vị thơm ngon của trứng. Bánh bông lan matcha mềm mịn, thơm ngon chắc chắn sẽ làm các bé thích mê1.\n" +
                        "\t\t+ Bánh mousse matcha: Bánh mousse matcha là sự kết hợp cực kỳ sáng tạo và mới lạ từ những nguyên liệu đơn giản. Bột trà xanh đắng nhẹ, thơm dịu kết hợp cùng whipping cream béo ngậy chắc chắn sẽ khiến cho những tâm hồn yêu đồ ngọt thích thú1.\n" +
                        "\t\t+ Bánh Chocopie Orion Matcha Trà Xanh: Lớp bánh xốp mịn với sô cô la chảy ngọt ngào bên ngoài đến lớp nhân marshmallow vị matcha thanh hấp dẫn ở bên trong2.", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh bông lan cuộn", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "Bánh bông lan cuộn là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan cuộn:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn đơn giản1: Công thức này giới thiệu cách làm bánh bông lan cuộn kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn Vanilla Cake Roll Recipe1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn kem tươi hoa quả2: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn hoa cúc1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh sầu riêng", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "Bánh sầu riêng là một loại bánh ngọt có hương vị đặc trưng của sầu riêng. Dưới đây là một số thông tin về các loại bánh sầu riêng phổ biến:\n" +
                        "\n" +
                        "\t\t+ Bánh Sầu Riêng TBT: Bánh được làm bằng sầu riêng Ri6 tươi nguyên chất, không sử dụng sầu kem sầu pha trộn. Vỏ bánh dai mềm mịn, không bị bột bở khi ăn. Lớp kem lạnh thơm béo, xen lẫn nhân sầu ngọt dịu1.\n" +
                        "\t\t+ Bánh Sầu Riêng: Chuyên sỉ & lẻ bánh sầu riêng ngàn lớp, bánh crepe. Với phương châm luôn đặt chất lượng lên hàng đầu, chúng tôi tự hào mang đến những sản phẩm tốt nhất cho khách hàng2.\n" +
                        "\t\t+ Bánh Sầu Riêng Chiên: Bánh Sầu Riêng Chiên giòn rụm thơm ngon tuyệt cú mèo3.\n" +
                        "\t\t+ Bánh Trung Thu Nhân Sầu Riêng: Bánh Trung Thu Nhân Sầu Riêng thơm nức mũi4", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh bông lan cuộn", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "Bánh bông lan cuộn là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan cuộn:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn đơn giản1: Công thức này giới thiệu cách làm bánh bông lan cuộn kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn Vanilla Cake Roll Recipe1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn kem tươi hoa quả2: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn hoa cúc1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh bông lan socola", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_socola, null)),
                "Bánh bông lan socola là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan socola:\n" +
                        "\n" +
                        "\t\t+ Cách làm Bánh Bông Lan Socola cực đơn giản chỉ trong 3 bước - PasGo1: Công thức này giới thiệu cách làm Bánh Bông Lan Socola kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm Bánh Bông Lan Socola Tươi | Chocolate Cake | Chị Mía2: Công thức này hướng dẫn cách làm Bánh Bông Lan Socola Tươi theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh bông lan socola cực đơn giản (chocolate sponge cake …3: Công thức này hướng dẫn cách làm bánh bông lan socola theo công thức như thế nào ngon nhất3.\n" +
                        "\t\t+ BÁNH BÔNG LAN SOCOLA – Công ty TNHH Đại Hiền Tâm4: Công thức này hướng dẫn cách làm bánh bông lan socola theo công thức như thế nào ngon nhất4", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh bông lan trứng muối", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "Bánh bông lan trứng muối là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan trứng muối:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan trứng muối (bằng nồi cơm điện hoặc lò nướng)1: Công thức này giới thiệu cách làm bánh bông lan trứng muối kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách Làm Bánh Bông Lan Trứng Muối Đơn Giản Tại Nhà2: Công thức này hướng dẫn cách làm bánh bông lan trứng muối theo công thức như thế nào ngon nhất", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh bông lan trứng muối", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "Bánh bông lan trứng muối là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan trứng muối:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan trứng muối (bằng nồi cơm điện hoặc lò nướng)1: Công thức này giới thiệu cách làm bánh bông lan trứng muối kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách Làm Bánh Bông Lan Trứng Muối Đơn Giản Tại Nhà2: Công thức này hướng dẫn cách làm bánh bông lan trứng muối theo công thức như thế nào ngon nhất", 4));
        cosmeticList.add(new Cosmetic(1, "Bánh su kem", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhsukem, null)),
                "Bánh su kem, còn được gọi là Choux à la crème, là một loại bánh ngọt có nguồn gốc từ nước Pháp. Bánh su kem có độ mềm dẻo, thơm ngậy đầy lôi cuốn1. Dưới đây là một số công thức nấu bánh su kem:\n" +
                        "\n" +
                        "\t\t+ Cách làm Bánh Su Kem (Choux à la crème) Ngon Béo Ngậy – bTaskee1: Công thức này giới thiệu cách làm bánh su kem kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Làm Bánh Su Kem Với Công Thức Dễ Thành Công Ngay Lần Đầu | Choux Pastry Recipe | Chị Mía2: Công thức này hướng dẫn cách làm bánh su kem theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Bánh Su kem (Choux à la Crème) – Demo & Một số lưu ý khi làm Choux3: Công thức này hướng dẫn cách làm bánh su kem theo công thức như thế nào ngon nhất", 4));

        // region Com suon
        cosmeticList.add(new Cosmetic(1, "Cơm sườn trứng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_trung, null)),
                "Cơm sườn trứng là một món ăn truyền thống của Việt Nam, thường được phục vụ trong bữa ăn hàng ngày. Dưới đây là cách làm cơm sườn trứng tại nhà1:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 1kg sườn cốt lết\n" +
                        "\t\t+ 400g thịt băm\n" +
                        "\t\t+ 3 quả trứng gà\n" +
                        "\t\t+ Hành tím, hành lá, hành tây, tỏi\n" +
                        "\t\t+ Gia vị: Mật ong, sữa tươi không đường, sữa đặc, dầu hào, giấm ăn, dầu ăn, nước tương, nước mắm, đường, muối, hạt nêm, tiêu xay1\n" +
                        "\nCách làm:\n" +
                        "\n" +
                        "\t\t+ Sơ chế sườn heo và ướp sườn: Rửa sạch sườn heo, sau đó dùng búa đập nhẹ nhàng lên 2 mặt thịt để thịt mềm và dễ ngấm gia vị hơn. Với sườn, bạn ướp sườn nướng như sau: 1 muỗng canh đường, 2 muỗng canh nước tương, 2 muỗng canh dầu hào, 2 muỗng canh nước mắm, 1 muỗng canh mật ong. Sau đó thêm vào 100ml sữa tươi không đường, 2 muỗng canh sữa đặc, 1 muỗng canh hành băm, 1 muỗng canh tỏi băm, 1 muỗng cà phê tiêu xay, ½ muỗng canh dầu ăn, ⅓ muỗng cà phê muối. Bạn trộn đều tất cả lên cùng thịt sườn. Sau đó cho phần thịt đã ướp vào hộp nhựa đậy nắp kín1.\n" +
                        "\t\t+ Sơ chế các nguyên liệu khác: Hành tây thái mỏng. Gừng, tỏi, sả đập dập. Cà chua cắt hạt lựu. Cà rốt tỉa hoa1.\n" +
                        "\t\t+ Pha nước mắm: Trong một cái chén nhỏ bạn cho vào: 3 muỗng canh nước mắm ngon (hoặc nước mắm chấm), sau đó cho tiếp vào chén: 2 muỗng canh dầu hào (hoặc dầu cá), tiếp theo là: 3 muỗng canh nước lọc (hoặc nước cốt dừa), cuối cùng bạn cho vào chén: 3 muỗng canh đường và khuấy đều1.\n" +
                        "\t\t+ Nướng thịt: Bạn cho từ từ phần thịt đã được ướp vào chảo và để lửa vừa phải1.\n" +
                        "\t\t+ Làm mỡ hành và tóp mỡ: Bạn cho từ từ phần thịt đã được ướp vào chảo và để lửa vừa phải1.\n" +
                        "\t\t+ Chiên trứng: Bạn cho từ từ phần thịt đã được ướp vào chảo và để lửa vừa phải1.", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn bì chả", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "Cơm sườn bì chả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm cơm tấm (cơm gạo tấm), sườn heo nướng, bì (da heo giòn) và chả (thường là chả trứng hoặc chả hấp). " +
                        "\n+ Cơm tấm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng. Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới. \n+ " +
                        "\t\t+ Bì được làm từ da heo, thường được luộc và xắt nhỏ. Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người. \n+ " +
                        "\t\t+ Món ăn thường được dùng kèm với nước mắm pha chua ngọt và rau sống. Cơm sườn bì chả là một món ăn ngon và bổ dưỡng, rất phù hợp cho bữa trưa hoặc bữa tối. \n+ " +
                        "\t\t+ Chúc bạn thưởng thức ngon miệng! \uD83D\uDE0A", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn nướng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "Cơm sườn nướng ả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm:\n" +
                        "\n" +
                        "\t\t+ Cơm tấm: Cơm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng.\n" +
                        "\t\t+ Sườn heo nướng: Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới.\n" +
                        "\t\t+ Bì (da heo giòn): Bì được làm từ da heo, thường được luộc và xắt nhỏ.\n" +
                        "\t\t+ Chả: Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người.", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn ram", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "Cơm sườn ram là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam. Món này bao gồm cơm trắng được phục vụ cùng với sườn heo đã được ướp với các loại gia vị và sau đó được ram cho tới khi chín tới. Dưới đây là một số công thức nấu Cơm sườn ram:\n" +
                        "\n" +
                        "\t\t+ Cách làm món sườn ram: 6 công thức ram sườn ngon khỏi chê1: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, ớt, đường, bột nêm, bột ngọt, nước mắm, nước màu trong khoảng 20 phút. Sau đó, sườn được chiên trong chảo dầu nóng. Khi sườn chín, lửa được tăng lớn để sườn lên màu1.\n" +
                        "\t\t+ Cách Làm Sườn Ram Mặn Ngọt Ngon Mềm, Đậm Đà Đưa Cơm2: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, tỏi, nước mắm, dầu hào, bột nêm, tiêu và nước hàng. Sau đó, sườn được chiên trong chảo dầu nóng. Khi miếng sườn đã rất mềm và thấm gia vị mặn ngọt đậm đà, thêm hành lá vào chảo và tắt bếp2.", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn bì chả", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "Cơm sườn bì chả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm cơm tấm (cơm gạo tấm), sườn heo nướng, bì (da heo giòn) và chả (thường là chả trứng hoặc chả hấp). \\n+ Cơm tấm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng. Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới. \\n+ \" +\n" +
                        "\"Bì được làm từ da heo, thường được luộc và xắt nhỏ. Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người. \\n+ \" +\n" +
                        "\"Món ăn thường được dùng kèm với nước mắm pha chua ngọt và rau sống. Cơm sườn bì chả là một món ăn ngon và bổ dưỡng, rất phù hợp cho bữa trưa hoặc bữa tối. \\n+ \" +\n" +
                        "\"Chúc bạn thưởng thức ngon miệng! \\uD83D\\uDE0A", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn nướng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "Cơm sườn nướng ả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Cơm tấm: Cơm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng.\\n\" +\n" +
                        "\"\\t\\t+ Sườn heo nướng: Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới.\\n\" +\n" +
                        "\"\\t\\t+ Bì (da heo giòn): Bì được làm từ da heo, thường được luộc và xắt nhỏ.\\n\" +\n" +
                        "\"\\t\\t+ Chả: Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người.", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn ram", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "Cơm sườn ram là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam. Món này bao gồm cơm trắng được phục vụ cùng với sườn heo đã được ướp với các loại gia vị và sau đó được ram cho tới khi chín tới. Dưới đây là một số công thức nấu Cơm sườn ram:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Cách làm món sườn ram: 6 công thức ram sườn ngon khỏi chê1: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, ớt, đường, bột nêm, bột ngọt, nước mắm, nước màu trong khoảng 20 phút. Sau đó, sườn được chiên trong chảo dầu nóng. Khi sườn chín, lửa được tăng lớn để sườn lên màu1.\\n\" +\n" +
                        "\"\\t\\t+ Cách Làm Sườn Ram Mặn Ngọt Ngon Mềm, Đậm Đà Đưa Cơm2: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, tỏi, nước mắm, dầu hào, bột nêm, tiêu và nước hàng. Sau đó, sườn được chiên trong chảo dầu nóng. Khi miếng sườn đã rất mềm và thấm gia vị mặn ngọt đậm đà, thêm hành lá vào chảo và tắt bếp2.", 3));
        cosmeticList.add(new Cosmetic(1, "Cơm sườn xào chua ngọt", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_chuangot, null)),
                "Cơm sườn xào chua ngọt là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam. Món này bao gồm cơm trắng được phục vụ cùng với sườn heo đã được ướp với các loại gia vị và sau đó được xào chung với nước sốt chua ngọt. Dưới đây là một số công thức nấu Cơm sườn xào chua ngọt:\n" +
                        "\n" +
                        "\t\t+ 6 Cách Làm Sườn Xào Chua Ngọt Đậm Đà, Hấp Dẫn, Ngon Đúng Điệu1: Công thức này giới thiệu cách làm sườn xào chua ngọt mặn ngọt. Sườn non được ướp với hành tím, ớt, đường, bột nêm, bột ngọt, nước mắm, nước màu trong khoảng 20 phút. Sau đó, sườn được chiên trong chảo dầu nóng. Khi sườn chín, lửa được tăng lớn để sườn lên màu1.\n" +
                        "\t\t+ Cách làm sườn xào chua ngọt ngon bất bại, vô cùng đơn giản2: Công thức này giới thiệu cách làm sườn xào chua ngọt. Sườn non được ướp với hành tím, tỏi, nước mắm, dầu hào, bột nêm, tiêu và nước hàng. Sau đó, sườn được chiên trong chảo dầu nóng. Khi miếng sườn đã rất mềm và thấm gia vị mặn ngọt thì thêm hành lá vào chảo và tắt bếp2.\n" +
                        "\t\t+ Cách làm sườn xào chua ngọt kiểu miền Nam vét sạch nồi cơm3: Công thức này hướng dẫn cách làm sườn xào chua ngọt theo kiểu miền Nam3.\n" +
                        "\t\t+ Sườn xào chua ngọt cho bữa cơm ngon hấp dẫn mỗi ngày4: Công thức này hướng dẫn cách làm sườn xào chua ngọt4.", 3));

        // region thebodyshop
        cosmeticList.add(new Cosmetic(1, "Sữa Rửa Mặt ", "Sữa Rửa Mặt ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhcanh, null)),
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
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bunmam, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Làn da của bạn sẽ trở nên tươi tắn và hồng hào hơn nhờ loại bơ tẩy trang dưỡng da mới có tông màu má hồng tuyệt đẹp.\n" +
                        "\t\t- Công thức bơ tẩy trang Camomile phiên bản giới hạn mới nhất của chúng tôi được làm giàu với dầu Rose de Mai quý giá từ Grasse, Pháp, sở hữu khả năng hòa tan lớp trang điểm trong vòng chưa đầy 30 giây. Nó phù hợp cho người đeo kính áp tròng và mọi loại da - thậm chí nhạy cảm. Để lại khuôn mặt của bạn cảm giác tươi mát và mềm mại như cánh hoa.\n" +
                        "\t\t- Mùi hương hoa hồng ngọt ngào của sản phẩm không chỉ giúp bạn thư giãn sau một ngày dài mệt mỏi mà nó còn giúp cuốn trôi đi lớp make-up nặng nề trên da mặt.\n" +
                        "\t\t- Hãy sở hữu loại sữa rửa mặt phiên bản giới hạn này bởi vì giống như mùa Rose de Mai, nó cực kỳ giới hạn về số lượng!\n" +
                        "\t\t+ Bơ tẩy trang phiên bản giới hạn\n"+
                        "\t\t+ Hương thơm hoa hồng Rose de Mai\n"+
                        "\t\t+ Thích hợp cho da nhạy cảm và người đeo kính áp tròng\n"+
                        "\t\t+ Được pha với dầu Rose de Mai từ Grasse, Pháp và dầu hoa cúc từ Norfolk, Anh\n"+
                        "\t\t+ Tẩy sạch những lớp trang điểm cứng đầu nhất, kể cả lớp trang điểm không thấm nước\n"+
                        "\t\t+ Làn da được nuôi dưỡng và mềm mượt\n"+
                        "\t\t+ Được chứng nhận bởi Hiệp hội thuần chay\n"+
                        "\t\t+ Đã được kiểm nghiệm da liễu\n", 6));

        cosmeticList.add(new Cosmetic(1, "Kem Chống Nắng", "Kem chống nắng ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Kem chống nắng Skin Defence Multi-Protection Light Essence sẽ đáp ứng nhu cầu bảo vệ da hàng ngày của bạn - hãy hứa với chúng tôi rằng bạn sẽ không rời khỏi nhà mà chưa có nó.\n" +
                        "\t\t- Tinh chất Skin Defence mới của chúng tôi là một công thức dưỡng ẩm siêu nhẹ, giúp bảo vệ làn da của bạn với độ bảo vệ SPF 50 PA +++. Tinh chất siêu lỏng này sử dụng các bộ lọc UVA/UVB phổ rộng được tối ưu hóa một cách thông minh để giúp bảo vệ da hàng ngày chống lại các tia UV gây hại, cũng như giúp bảo vệ khỏi ô nhiễm trong nhà và ngoài trời.\n" +
                        "\t\t- Công thức hấp thụ nhanh, tạo cảm giác thoáng khí và thoải mái trên da. Sản phẩm không gây nhờn dính, không để lại vệt trắng trên da bạn. Da trông rạng rỡ hơn, đều màu và sẵn sàng để trang điểm.\n" +
                        "\t\t+ Tinh chất dưỡng ẩm thoáng nhẹ, thẩm thấu nhanh\n"+
                        "\t\t+ SPF 50 PA+++ với khả năng chống ô nhiễm trong nhà và ngoài trời\n"+
                        "\t\t+ Bảo vệ da khỏi tia UVA / UVB\n"+
                        "\t\t+ Không để lại cảm giác dính hoặc nhờn trên da\n"+
                        "\t\t+ Lớp lót hoàn hảo trước khi trang điểm\n"+
                        "\t\t+ Thích hợp cho da nhạy cảm\n"+
                        "\t\t+ Được chứng nhận bởi Hiệp hội thuần chay\n"+
                        "\t\t+ Không gây mụn\n", 6));

        cosmeticList.add(new Cosmetic(1, "Kem Dưỡng Ẩm", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Kem giữ ẩm dạng gel Seaweed Oil-Control Gel Cream giữ lại hơi nước, không dầu, không bịt kín chân lông, và thẩm thấu dễ dàng.\n" +
                        "\t\t- Kem kiểm soát bã nhờn và độ bóng của da trong khi cung cấp độ ẩm cần thiết cho các vùng da có nhu cầu nhất, cân bằng da suốt cả ngày. Cho làn da tươi khỏe, sạch, mịn, và mềm mại, không bị bóng do dầu nhờn.\n" +
                        "\t\t+ Phù hợp cho da dầu hoặc hỗn hợp thiên dầu\n"+
                        "\t\t+ ảo biển giàu khoáng chất từ vịnh Roaring Water, Ireland\n"+
                        "\t\t+ Cấu tạo dịu nhẹ\n"+
                        "\t\t+ Cân bằng dầu và khô thoáng\n", 6));

        cosmeticList.add(new Cosmetic(1, "Bông Tẩy Trang", "Bông Tẩy Trang",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Bông Tẩy Trang hữu cơ của The Body Shop hoàn chỉnh với hai bề mặt không có lông tơ đáp ứng mọi nhu cầu làm đẹp của bạn.\n" +
                        "\t\t- Một sản phẩm thiết yếu cần phải có trong mọi phòng tắm. Cho làn da tươi khỏe, sạch, mịn, và mềm mại, không bị bóng do dầu nhờn.\n" +
                        "\t\t+ Hữu cơ\n"+
                        "\t\t+ Hai mặt\n"+
                        "\t\t+ Sử dụng với sữa rửa mặt hoặc toner\n"+
                        "\t\t+ Hai bề mặt có kết cấu khác nhau cho kem dưỡng da hoặc chất lỏng\n", 6));

        cosmeticList.add(new Cosmetic(1, "Tẩy Tế Bào Chết ", "Tẩy Tế Bào Chết ",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Điểm nổi bật\n" +
                        "\n" +
                        "\t\t- Nhẹ nhàng xoa dịu cơ thể xinh đẹp trở nên mềm mại như lụa mà không để lại cảm giác da bị mất đi lớp dầu tự nhiên với sản phẩm Tẩy tế bào chết toàn thân Avocado của chúng tôi.\n" +
                        "\t\t- Với 96% thành phần có nguồn gốc tự nhiên, bao gồm cả dầu bơ Hass có nguồn gốc bền vững từ Nam Phi, sản phẩm tẩy tế bào chết dạng kem và giàu dưỡng chất của chúng tôi sẽ loại bỏ các tế bào da chết mang lại cảm giác da mềm mại và mịn màng hơn.\n" +
                        "\t\t- Sản phẩm tẩy da chết có mùi tươi mát này hiện được đóng gói trong bao bì có thể tái chế.\n"+
                        "\n\n Điểm đặc biệt:\n"+
                        "\t\t+ Tẩy tế bào chết ngậm nước cho cơ thể\n"+
                        "\t\t+ Hoàn hảo cho da khô\n"+
                        "\t\t+ Để lại làn da cảm giác mềm mại và mịn màng hơn\n"+
                        "\t\t+ Mùi thơm tinh tế tươi mát và kem\n"+
                        "\t\t+ Được chứng nhận bởi Hiệp hội thuần chay\n", 6));

        cosmeticList.add(new Cosmetic(1, "Lăn Khử Mùi", "Lăn Khử Mùi",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "Cảm nhận hương thơm thơm mát với Maca Root & Aloe Fresh Kick Deodorant được chiết xuất từ rễ cây maca, dầu hạt cộng đồng Brazil và dầu hữu cơ trong chương trình Thương Mại Cộng Đồng.\n" +
                        "\n" +
                        "\t\t+ Thanh khử mùi\n"+
                        "\t\t+ Làm mới mùi hương\n"+
                        "\t\t+ Chiết xuất từ rễ cây maca và dầu hạt từ Brazil, lô hội hữu cơ trong chương trình Thương mại Cộng đồng từ Mexico\n", 6));

        // region Tra sua
        cosmeticList.add(new Cosmetic(1, "Trà sữa dâu", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_dau, null)),
                "Trà sữa dâu là một món đồ uống ngọt ngào, thơm mát rất được ưa chuộng. Dưới đây là cách làm trà sữa dâu tại nhà:\n" +
                        "\n" +
                        "Nguyên liệu: Trà đen (trà cánh hoặc trà túi lọc), dâu tươi hoặc siro dâu để tạo hương vị và trang trí, sữa đặc hoặc bột pha trà sữa chuyên dụng để tạo độ thơm béo, sữa tươi (tùy chọn để thay thế cho sữa đặc và bột sữa), đường kính hoặc siro đường để tạo độ ngọt, và đá viên12.\n" +
                        "\n" +
                        "Cách làm:\n" +
                        "\n" +
                        "\t\t+ Cách 1: Sử dụng trà túi lọc vị dâu1. Ủ trà với nước nóng trong khoảng 15 phút, sau đó lọc lấy nước cốt. Cho sữa đặc, siro dâu, đường kính vào trà nóng và hòa tan. Cho hỗn hợp vào bình lắc, thêm đá viên, đóng nắp bình và lắc khoảng 15 lần. Đổ trà sữa dâu ra ly, thêm topping và thưởng thức1.\n" +
                        "\t\t+  Cách 2: Sử dụng nước ép dâu tươi1. Cho dâu tây vào máy xay, xay nhuyễn, dùng rây lọc lấy nước cốt dâu. Hòa tan nước cốt trà, nước cốt dâu, bột pha trà sữa chuyên dụng, siro đường. Cho hỗn hợp vào bình lắc, thêm đá viên, đóng nắp bình và lắc khoảng 15 lần. Đổ trà sữa dâu ra ly, thêm topping, trang trí thêm lát dâu tươi và bạc hà tùy thích1.\n" +
                        "\t\t+ Cách 3: Sử dụng siro dâu và sữa tươi1. Trộn đường và siro dâu với trà đã ủ. Cho sữa tươi vào ly rồi rót trà vào khuấy đều. Thêm đá và trân châu (nếu muốn) vào trà sữa3.", 2));
        cosmeticList.add(new Cosmetic(1, "Trà sữa matcha", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_matcha, null)),
                "Trà sữa Matcha là một món đồ uống thơm ngon, béo ngậy từ sữa và có vị đắng nhẹ cùng hương thơm từ matcha1. Dưới đây là cách làm trà sữa Matcha tại nhà:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 3 gram bột Matcha (có thể thay thế bằng bột trà xanh)\n" +
                        "\t\t+ 100ml sữa tươi không đường (hay 70 gram bột sữa)\n" +
                        "\t\t+ 30ml sữa đặc\n" +
                        "\t\t+ 1 gói trà xanh túi lọc\n" +
                        "\t\t+ Đá viên2\n" +
                        "Cách pha trà sữa Matcha:\n" +
                        "\n" +
                        "\t\t+ Cho túi trà xanh vào 50ml nước nóng, nên để nhiệt độ khoảng 80 độ C. Không đậy nắp và ủ trà trong 2 – 3 phút rồi lấy túi trà ra.\n" +
                        "\t\t+ Cho vào ly và khuấy tan hỗn hợp gồm: 3 gram bột Matcha, 15 gram bột sữa, 50ml nước cốt trà xanh túi lọc.\n" +
                        "\t\t+ Chuẩn bị ly khác, cho 30ml sữa đặc và 50ml nước cốt trà xanh đun nóng vào và khuấy đến khi hòa quyện.\n" +
                        "\t\t+ Cho hỗn hợp sữa đã khuấy ở trên cùng đá viên vào bình lắc. Đậy nắp bình và lắc khoảng 3 giây cho hỗn hợp hòa quyện.\n" +
                        "\t\t+ Rót sữa lắc ra ly thủy tinh, rót phần hỗn hợp Matcha đã pha lên trên tạo thành lớp tầng rất đẹp mắt2.", 2));
        cosmeticList.add(new Cosmetic(1, "Trà sữa truyền thống", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_truyenthong, null)),
                "Trà sữa truyền thống là một món đồ uống ngon và thơm mát, rất được ưa chuộng. Dưới đây là cách làm trà sữa truyền thống tại nhà:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 70g trà đen\n" +
                        "\t\t+ 200g đường trắng\n" +
                        "\t\t+ 250g bột sữa\n" +
                        "\t\t+ 100ml sữa đặc1\n" +
                        "Cách làm:\n" +
                        "\n" +
                        "\t\t+ Nấu sôi 1 lít nước, cho 70g trà vào khuấy đều rồi ủ 15 phút sau đó lọc lấy nước trà1.\n" +
                        "\t\t+ Cho 200g đường trắng và 250g bột sữa vào nước trà và khuấy đều1.\n" +
                        "\t\t+ Cuối cùng cho 100ml sữa đặc vào. Khuấy cho các nguyên liệu tan hoàn toàn thì thêm 300g đá viên vào khuấy cho tan1.\n" +
                        "\t\t+ Lúc này có thể lọc qua rây để đảm bảo trà không còn cặn1.", 2));
        cosmeticList.add(new Cosmetic(1, "Trà sữa xoài", "Trà sữa",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.trasua_xoai, null)),
                "Trà sữa xoài là một món đồ uống thơm ngon, mát lạnh và vô cùng lý tưởng cho những ngày hè nóng nực. Dưới đây là cách làm trà sữa xoài tại nhà:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 50 gr trà đen hoặc trà lài\n" +
                        "\t\t+ 200 gr bột sữa\n" +
                        "\t\t+ 300 gr đường trắng hoặc đường nâu\n" +
                        "\t\t+ 1 gr muối\n" +
                        "\t\t+ 1.3l nước sôi\n" +
                        "\t\t+ Đá viên\n" +
                        "\t\t+ 1l nước sôi để nguội1\n" +
                        "\t\t+ Xoài chín bỏ vỏ và hạt, cắt nhỏ2\n" +
                        "\t\t+ Siro đào, siro chanh dây2\n" +
                        "Cách làm:\n" +
                        "\n" +
                        "\t\t+ Nấu sôi 1 lít nước, cho 50g trà vào khuấy đều rồi ủ 15 phút sau đó lọc lấy nước trà1.\n" +
                        "\t\t+ Cho vào ly và khuấy tan hỗn hợp gồm: 200g bột sữa, 300g đường trắng hoặc đường nâu, và 1.3l nước sôi1.\n" +
                        "\t\t+ Cho xoài đã cắt nhỏ vào máy xay sinh tố, thêm siro đào, siro chanh dây, một chút đá viên vào rồi xay nhuyễn2.\n" +
                        "\t\t+ Ngâm trà với 150ml nước nóng, sau 10 phút lọc bã lấy nước cốt, hòa tan cùng đường và để nguội bớt2.\n" +
                        "\t\t+ Cho đá viên vào ly, đổ nước trà vào. Xúc hỗn hợp xoài lên trên cùng là hoàn thành2.", 2));

        // region cosmeticSize
        cosmeticSizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 55; i++) {
            cosmeticSizeList.add(new CosmeticSize(i, 1, (random.nextInt(20) + 1) * 1000d));
            cosmeticSizeList.add(new CosmeticSize(i, 2, (random.nextInt(20) + 21) * 1000d));
            cosmeticSizeList.add(new CosmeticSize(i, 3, (random.nextInt(20) + 41) * 1000d));
        }

        // region cosmeticSaved
        cosmeticSavedList = new ArrayList<>();
        cosmeticSavedList.add(new CosmeticSaved(1, 3, 1));
        cosmeticSavedList.add(new CosmeticSaved(36, 3, 2));
        cosmeticSavedList.add(new CosmeticSaved(3, 3, 2));
        cosmeticSavedList.add(new CosmeticSaved(42, 3, 2));
        cosmeticSavedList.add(new CosmeticSaved(11, 3, 1));
        cosmeticSavedList.add(new CosmeticSaved(28, 1, 4));
        cosmeticSavedList.add(new CosmeticSaved(40, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(3, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(42, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(31, 3, 3));
        cosmeticSavedList.add(new CosmeticSaved(20, 1, 4));




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
