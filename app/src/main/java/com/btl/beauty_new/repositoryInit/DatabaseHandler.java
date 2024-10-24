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
        // region Kem
        cosmeticList.add(new Cosmetic(1, "Kem hộp đậu đỏ", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_daudo, null)),
                "Kem hộp đậu đỏ là một món tráng miệng ngon và mát lạnh, rất thích hợp cho những ngày hè nóng bức. Dưới đây là cách làm kem hộp đậu đỏ tại nhà:\n" +
                        "\n" +
                        "Nguyên liệu:\n" +
                        "\n" +
                        "\t\t+ 250g đậu đỏ1\n" +
                        "\t\t+ 35ml sữa tươi1\n" +
                        "\t\t+ 150g đường nâu1\n" +
                        "\t\t+ 1/4 muỗng cà phê muối1\n" +
                        "\t\t+ 1/2 muỗng cà phê mật ong1\n" +
                        "\t\t+ 40g sữa đặc1\n" +
                        "\nCách làm:\n" +
                        "\n" +
                        "\t\t+ Đậu đỏ vo sạch, sau đó ngâm nước 5-6 tiếng cho đỗ nở mềm1.\n" +
                        "\t\t+ Cho đỗ vào nồi, sau đó cho nước ngập mặt đỗ (cỡ 1 đốt ngón tay). Bật bếp và không cần đậy nắp nồi, nấu cho tới khi đỗ chín mềm và nước cũng rút cạn thì tắt bếp1.\n" +
                        "\t\t+ Khi thấy đậu chín mềm thì cho thêm 100g đường vào, khuấy đều. Khi đậu đã chín, bạn vớt ra để nguội1.\n" +
                        "\t\t+ Lấy 2/3 phần đậu vừa nấu vào máy sinh tố, để nguội bớt thì thêm sữa tươi, sữa đặc vào và xay nhuyễn2.\n" +
                        "\t\t+ Cho hỗn hợp vừa xay vào khuôn kem đã chuẩn bị sẵn2.\n" +
                        "\t\t+ Giờ thì cùng cho vào ngăn đông của tủ lạnh, bạn để khoảng 7-8 giờ là có thể thưởng thức món kem thơm ngon, mát lạnh này rồi2.", 5));
        cosmeticList.add(new Cosmetic(1, "Kem hộp sữa dừa", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_suadua, null)),
                "Kem hộp sữa dừa là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của sữa dừa. \n\n- Dưới đây là một số thông tin về các loại kem hộp sữa dừa phổ biến:\n" +
                        "\t\t+ Kem hộp sữa dừa Merino 900ml: Sản phẩm này kết hợp độc đáo giữa sầu riêng tự nhiên và sữa dừa mềm béo thơm bùi khó cưỡng, vị ngon hoàn hảo1. Giá bán tại Bách hóa XANH khoảng 84.000 VND.\n" +
                        "\t\t+ Kem ly sữa dừa Merino 53g: Giá bán khoảng 13.000 VND, kem vẫn giữ nguyên nét thơm ngon đặc trưng truyền thống, cực hợp khẩu vị của người Việt, mang đến hương vị độc đáo cực ngon miệng.\n" +
                        "\t\t+ Hộp sữa dừa 450ml - Kem Merino: Kem hộp Merino thơm ngon với các hương vị truyền thống quen thuộc luôn là món tráng miệng tuyệt vời cho cả gia đình.", 5));
        cosmeticList.add(new Cosmetic(1, "Kem ốc quế vani", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_vani, null)),
                "Kem ốc quế vani là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của vani. \n\n- Dưới đây là một số thông tin về các loại kem ốc quế vani phổ biến:\n" +
                        "\t\t+ Kem ốc quế vani socola Celano 110ml: Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sô cô la ngọt ngào, vừa đậm đà đầy kích thích1. Giá bán tại Bách hóa XANH khoảng 20.500 VND1.\n" +
                        "\t\t+ Kem Ốc Quế Crunchy Classic Vani 70g: Sản phẩm này có vị vani hấp dẫn cùng phần ốc quế làm từ bánh quy giòn thơm hòa quyện với cái lạnh mát của kem mang lại bạn sự ngọt ngào khó tả không thể bỏ qua2. Giá bán khoảng 19.000 VND2.\n" +
                        "\t\t+ Kem ốc quế Merino Superteen vị vani socola 60g: Sản phẩm này được sản xuất từ nguồn nguyên liệu tươi ngon, đảm bảo chất lượng. Vị kem mát lạnh, kết hợp với vị ngọt dịu của dâu và hương vani thơm mát, mang lại sự ngon miệng, sảng khoái cho người thưởng thức3.\n" +
                        "\t\t+ Kem vani socola Merino Super Teen cây 60g: Sản phẩm này là dạng kem cây ốc quế vô cùng thơm ngon và tiện dụng với hương vị vani socola hòa quyện độc đáo4.", 5));
        cosmeticList.add(new Cosmetic(1, "Kem ôc quế dâu", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem ốc quế dâu là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của dâu. \n\n-  Dưới đây là một số thông tin về các loại kem ốc quế dâu phổ biến:\n" +
                        "\t\t+ Kem ốc quế dâu Celano Extra cây 125ml (73g): Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sữa kem béo nhẹ & mứt dâu1. Giá bán tại Bách hóa XANH khoảng 24.000 VND1.\n" +
                        "\t\t+ Kem ốc quế vani dâu Celano cây 66g: Sản phẩm này có vị thơm ngon khó cưỡng, giúp hạ nhiệt, giải khát vô cùng hiệu quả cho các ngày nắng nóng2. Giá bán khoảng 20.000 VND2.\n" +
                        "\t\t+ Kem Ốc Quế Delight Dâu - Nam Việt Quất 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn, thơm lừng mang đến cho bạn cảm giác thật mát lạnh & ngon khó cưỡng ngay từ miếng cắn đầu tiên3", 5));
        cosmeticList.add(new Cosmetic(1, "Kem ốc quế socola", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Kem ốc quế socola là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của socola. \n\n- Dưới đây là một số thông tin về các loại kem ốc quế socola phổ biến:\n" +
                        "\t\t+ Kem ốc quế socola King’s cây 80g: Sản phẩm này với các nguyên liệu tự nhiên cao cấp, không sử dụng các hóa chất độc hại. Kem ốc quế socola King’s cây 80g vị socola đậm đà với thiết kế ấn tượng, bắt mắt, hương vị thơm béo càng làm cho sản phẩm thêm phần hấp dẫn1.\n" +
                        "\t\t+ Kem ốc quế socola extra Celano cây 75g: Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sô cô la extra đầy độc đáo, béo thơm, vừa đậm đà đầy kích thích2.\n" +
                        "\t\t+ Kem ốc quế Merino Superteen vị vani socola 60g: Sản phẩm này được sản xuất từ nguồn nguyên liệu tươi ngon, đảm bảo chất lượng. Vị kem mát lạnh, kết hợp với vị ngọt dịu của dâu và hương vani thơm mát, mang lại sự ngon miệng, sảng khoái cho người thưởng thức3.\n" +
                        "\t\t+ Kem Ốc Quế TH true ICE CREAM Sô Cô La Nguyên Chất: Sản phẩm được làm từ sữa tươi sạch nguyên chất của Trang trại TH cùng các nguyên liệu hoàn toàn tự nhiên. Sản phẩm có lớp kem sô cô la nguyên chất mềm mịn, được phủ sốt sô cô la cùng hạt đậu phộng thơm ngậy trên bề mặt, cuộn trong vỏ bánh ốc quế giòn tan bên ngoài4.\n" +
                        "\t\t+ Kem Ốc Quế Delight Sôcôla - Đậu Phộng 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn", 5));
        cosmeticList.add(new Cosmetic(1, "Kem ôc quế dâu", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem ốc quế dâu là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của dâu. \\n\\n-  Dưới đây là một số thông tin về các loại kem ốc quế dâu phổ biến:\\n\" +\n" +
                        "\"\\t\\t+ Kem ốc quế dâu Celano Extra cây 125ml (73g): Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sữa kem béo nhẹ & mứt dâu1. Giá bán tại Bách hóa XANH khoảng 24.000 VND1.\\n\" +\n" +
                        "\"\\t\\t+ Kem ốc quế vani dâu Celano cây 66g: Sản phẩm này có vị thơm ngon khó cưỡng, giúp hạ nhiệt, giải khát vô cùng hiệu quả cho các ngày nắng nóng2. Giá bán khoảng 20.000 VND2.\\n\" +\n" +
                        "\"\\t\\t+ Kem Ốc Quế Delight Dâu - Nam Việt Quất 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn, thơm lừng mang đến cho bạn cảm giác thật mát lạnh & ngon khó cưỡng ngay từ miếng cắn đầu tiên3", 5));
        cosmeticList.add(new Cosmetic(1, "Kem ốc quế socola", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Kem ốc quế socola là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của socola. \n\n- Dưới đây là một số thông tin về các loại kem ốc quế socola phổ biến:\n" +
                        "\t\t+ Kem ốc quế socola King’s cây 80g: Sản phẩm này với các nguyên liệu tự nhiên cao cấp, không sử dụng các hóa chất độc hại. Kem ốc quế socola King’s cây 80g vị socola đậm đà với thiết kế ấn tượng, bắt mắt, hương vị thơm béo càng làm cho sản phẩm thêm phần hấp dẫn.\n" +
                        "\t\t+ Kem ốc quế socola extra Celano cây 75g: Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sô cô la extra đầy độc đáo, béo thơm, vừa đậm đà đầy kích thích.\n" +
                        "\t\t+ Kem ốc quế Merino Superteen vị vani socola 60g: Sản phẩm này được sản xuất từ nguồn nguyên liệu tươi ngon, đảm bảo chất lượng. Vị kem mát lạnh, kết hợp với vị ngọt dịu của dâu và hương vani thơm mát, mang lại sự ngon miệng, sảng khoái cho người thưởng thức.\n" +
                        "\t\t+ Kem Ốc Quế TH true ICE CREAM Sô Cô La Nguyên Chất: Sản phẩm được làm từ sữa tươi sạch nguyên chất của Trang trại TH cùng các nguyên liệu hoàn toàn tự nhiên. Sản phẩm có lớp kem sô cô la nguyên chất mềm mịn, được phủ sốt sô cô la cùng hạt đậu phộng thơm ngậy trên bề mặt, cuộn trong vỏ bánh ốc quế giòn tan bên ngoài.\n" +
                        "\t\t+ Kem Ốc Quế Delight Sôcôla - Đậu Phộng 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn.", 5));

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

        // region Olay
        cosmeticList.add(new Cosmetic(1, "Tinh Chất Olay Chống Lão Hóa Chứa Chiết Xuât Retinol24 30ml", "Tinh chất",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.olay_cosmetic1, null)),
                "Serum OLAY RETINOL24: LIỆU TRÌNH TRẺ HÓA DA BAN ĐÊM, GIÚP MỜ NẾP NHĂN RÕ RỆT SAU 28 NGÀY\n" +
                        "\nRetinol là một dẫn xuất của Vitamin A, có tác dụng trung hòa các gốc tự do, kích thích sản sinh collagen và tái tạo bề mặt da. Retinol giúp làm mờ thâm nám, cải thiện lỗ chân lông và mụn.\n" +
                        "\nOlay Retinol24 kết hợp phức hợp độc quyền giữa Niacinamide, Retinol và Retinyl Propionate, giúp trẻ hóa da hiệu quả mà không gây kích ứng mạnh. Serum giúp mờ nếp nhăn, đều màu da, mờ đốm nâu, và tăng cường độ săn chắc cho da.\n" +
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
                        "- Chiết xuất Carob hỗ trợ phục hồi tổn thương bề mặt da.",4));
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


        // region neutro
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
                        "\nCông thức kem chống nắng dành cho da mặt thể thao của chúng tôi khai thác sức mạnh của Công nghệ Helioplex® để cung cấp khả năng chống tia UVA/UVB phổ rộng vượt trội khỏi ánh nắng mặt trời. Nó cũng chống nước (lên đến 80 phút), cọ xát, mồ hôi và lau để bạn có thể tiếp tục hoạt động mà không lo bị cháy nắng.\n" +
                        "\n- Non-comedogenic (không làm tắc nghẽn lỗ chân lông)\n" +
                        "- Không chứa dầu\n" +
                        "- Không chứa PABA\n" +
                        "- Chống mồ hôi/chống nước (80 phút)\n" +
                        "\nCÁCH SỬ DỤNG\n" +
                        "- Bôi Kem Chống Nắng Neutrogena Sport Face SPF 70 trước 30 phút khi đi ra đường để kem kịp thấm vào da và phát huy tác dụng chống nắng một cách tối đa.\n" +
                        "- Chỉ cần bôi một lớp kem mỏng. Bôi quá nhiều vừa gây lãng phí vừa không tốt cho da vì lớp kem thừa không kịp thấm vào da sẽ có thể là nguyên nhân gây nên bệnh dị ứng da trong mùa hè.",3));
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
                        "- Bác sĩ da liễu đã thử nghiệm và chứng minh lâm sàng là nhẹ nhàng trên da nhạy cảm.",3));









        // region Mon nuoc
        cosmeticList.add(new Cosmetic(1, "Bánh canh", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhcanh, null)),
                "Bánh canh là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam và miền Trung. Bánh canh bao gồm nước dùng được nấu từ tôm, cá và giò heo thêm gia vị tùy theo từng loại bánh canh1. Sợi bánh canh có thể được làm từ bột gạo, bột mì, bột năng hoặc bột sắn hoặc bột gạo pha bột sắn12. Bánh được làm từ bột được cán thành tấm và cắt ra thành sợi to và ngắn1. Gia vị cho bánh canh thay đổi tùy theo món bánh canh và tùy theo khẩu vị mỗi vùng1.\n" +
                        "\n" +
                        "Có nhiều loại bánh canh khác nhau như:\n" +
                        "\n" +
                        "\t\t+ Bánh canh cua: Với những miếng thịt cua đỏ tươi được xếp ở bên trên, khi ăn bạn sẽ cảm nhận được sự dai ngọt của thịt cua3.\n" +
                        "\t\t+ Bánh canh ghẹ: Thơm ngọt của thịt ghẹ, làm tăng thêm hương vị cho nước dùng3.\n" +
                        "\t\t+ Bánh canh chay: Phần nước dùng từ rau củ đậm đà ngọt thanh và cực kì bổ dưỡng3.\n" +
                        "\t\t+ Bánh canh thịt bằm: Món ăn sáng phổ biến của nhiều gia đình3.\n" +
                        "\t\t+ Bánh canh cá lóc: Nước dùng sóng sánh, thịt cá lóc thơm ngọt, dai dai mà lại không hề bị tanh", 6));
        cosmeticList.add(new Cosmetic(1, "Bún mắm", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bunmam, null)),
                "Bún mắm là một món ăn đặc trưng của Việt Nam, đặc biệt là miền Tây, với hương vị đậm đà và phong cách riêng biệt. Dưới đây là mô tả chi tiết về món ăn này:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu chính: Bún mắm chủ yếu được làm từ cá lóc phi lê, thịt heo quay, mắm linh, sặc (mắm cá), tôm sú, dưa, cà tím, sả cay, sả bằm và nước thơm1.\n" +
                        "\t\t+ Hương vị: Bún mắm có hương vị đậm đà từ nước lèo thơm nồng kết hợp với mùi nắm cá linh và cá sặc2. Món ăn này còn được bổ sung thêm hương vị từ các loại rau đặc trưng của miền Tây2.\n" +
                        "\t\t+ Cách chế biến: Bún mắm được chế biến bằng cách hấp các nguyên liệu chính như cá lóc phi lê, thịt heo quay và tôm sú. Sau đó, các nguyên liệu này được kết hợp với nước lèo thơm nồng để tạo ra hương vị đặc trưng cho món ăn1.\n" +
                        "\t\t+ Phục vụ: Bún mắm thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 9986 đánh giá1.", 6));
        cosmeticList.add(new Cosmetic(1, "Bún thái", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "Bún Thái là một món ăn đặc trưng của Thái Lan, với hương vị chua cay đặc trưng. Dưới đây là cách chế biến món Bún Thái:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Bún Thái chủ yếu được làm từ thịt bò fillet mềm, râu mực, tôm sú tươi, nghêu to, rau muống, cà chua, nấm đông cô tươi, gừng cắt sợi, riềng cắt sợi và hành tím1.\n" +
                        "\t\t+ Hương vị: Bún Thái có hương vị chua chua, cay cay đặc trưng của xứ Thái2. Hương vị này được tạo ra từ việc kết hợp các loại gia vị đặc trưng trong ẩm thực Thái2.\n" +
                        "\t\t+ Cách chế biến: Bún Thái được chế biến bằng cách xào thơm các nguyên liệu như thịt bò, râu mực và tôm sú. Sau đó, các nguyên liệu này được kết hợp với nước dùng thơm nồng để tạo ra hương vị đặc trưng cho món ăn1.\n" +
                        "\t\t+ Phục vụ: Bún Thái thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 21339 đánh giá1.", 6));
        cosmeticList.add(new Cosmetic(1, "Hoành thánh", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "Hoành thánh là một món ăn đặc trưng của Trung Quốc và đã trở nên phổ biến ở Việt Nam. Dưới đây là cách chế biến món hoành thánh:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Hoành thánh chủ yếu được làm từ thịt nạc vai xay, thịt tôm xay, cà rốt, nấm hương, hành tây, tỏi, hành tím và hành lá1.\n" +
                        "\t\t+ Hương vị: Hoành thánh có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Hoành thánh được chế biến bằng cách nặn từng viên hoành thánh rồi nấu trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hoành thánh thường được phục vụ nóng hổi cùng với nước dùng thanh mát1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 1 đánh giá1.", 6));
        cosmeticList.add(new Cosmetic(1, "Hủ tiếu bò kho", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Hủ tiếu bò kho là một món ăn ngon, đậm đà được nhiều người yêu thích. Dưới đây là cách chế biến món hủ tiếu bò kho:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Hủ tiếu bò kho chủ yếu được làm từ thịt bò nạm, hủ tiếu, giá, màu dầu điều, hạt nêm, đường, muối, ớt bột, tiêu, bột gia vị bò kho, sả, ngò gai, hành lá, hành tím, hành tây, ớt, tỏi, quế, chanh1.\n" +
                        "\t\t+ Hương vị: Hủ tiếu bò kho có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Hủ tiếu bò kho được chế biến bằng cách ướp thịt bò trong 30 phút với gia vị bò kho, màu dầu điều, đường, hạt nêm, tiêu, ớt bột và muối. Sau đó thịt được xào sơ trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hủ tiếu bò kho thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1", 6));
        cosmeticList.add(new Cosmetic(1, "Hủ tiếu nam vang", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Hủ tiếu Nam Vang là một món ăn đặc trưng của Việt Nam, với hương vị đậm đà và phong cách riêng biệt. Dưới đây là mô tả chi tiết về món ăn này:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu chính: Hủ tiếu Nam Vang chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, trứng cút, tỏi, hành lá, cần tàu, xà lách, giá và hủ tiếu1.\n" +
                        "\t\t+ Hương vị: Hủ tiếu Nam Vang có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Hủ tiếu Nam Vang được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hủ tiếu Nam Vang thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1.", 6));
        cosmeticList.add(new Cosmetic(1, "Bún thái", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "Bún Thái là một món ăn đặc trưng của Thái Lan, với hương vị chua cay đặc trưng. Dưới đây là cách chế biến món Bún Thái:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Bún Thái chủ yếu được làm từ thịt bò fillet mềm, râu mực, tôm sú tươi, nghêu to, rau muống, cà chua, nấm đông cô tươi, gừng cắt sợi, riềng cắt sợi và hành tím1.\n" +
                        "\t\t+ Hương vị: Bún Thái có hương vị chua chua, cay cay đặc trưng của xứ Thái2. Hương vị này được tạo ra từ việc kết hợp các loại gia vị đặc trưng trong ẩm thực Thái2.\n" +
                        "\t\t+ Cách chế biến: Bún Thái được chế biến bằng cách xào thơm các nguyên liệu như thịt bò, râu mực và tôm sú. Sau đó, các nguyên liệu này được kết hợp với nước dùng thơm nồng để tạo ra hương vị đặc trưng cho món ăn1.\n" +
                        "\t\t+ Phục vụ: Bún Thái thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 21339 đánh giá1", 6));
        cosmeticList.add(new Cosmetic(1, "Hoành thánh", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "Hoành thánh là một món ăn đặc trưng của Trung Quốc và đã trở nên phổ biến ở Việt Nam. Dưới đây là cách chế biến món hoành thánh:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Hoành thánh chủ yếu được làm từ thịt nạc vai xay, thịt tôm xay, cà rốt, nấm hương, hành tây, tỏi, hành tím và hành lá1.\n" +
                        "\t\t+ Hương vị: Hoành thánh có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+  Cách chế biến: Hoành thánh được chế biến bằng cách nặn từng viên hoành thánh rồi nấu trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hoành thánh thường được phục vụ nóng hổi cùng với nước dùng thanh mát1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 1 đánh giá1.", 6));
        cosmeticList.add(new Cosmetic(1, "Hủ tiếu bò kho", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Hủ tiếu bò kho là một món ăn ngon, đậm đà được nhiều người yêu thích. Dưới đây là cách chế biến món hủ tiếu bò kho:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Nguyên liệu: Hủ tiếu bò kho chủ yếu được làm từ thịt bò nạm, hủ tiếu, giá, màu dầu điều, hạt nêm, đường, muối, ớt bột, tiêu, bột gia vị bò kho, sả, ngò gai, hành lá, hành tím, hành tây, ớt, tỏi, quế, chanh1.\\n\" +\n" +
                        "\"\\t\\t+ Hương vị: Hủ tiếu bò kho có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\\n\" +\n" +
                        "\"\\t\\t+ Cách chế biến: Hủ tiếu bò kho được chế biến bằng cách ướp thịt bò trong 30 phút với gia vị bò kho, màu dầu điều, đường, hạt nêm, tiêu, ớt bột và muối. Sau đó thịt được xào sơ trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\\n\" +\n" +
                        "\"\\t\\t+ Phục vụ: Hủ tiếu bò kho thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\\n\" +\n" +
                        "\"\\t\\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1", 6));
        cosmeticList.add(new Cosmetic(1, "Hủ tiếu nam vang", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Hủ tiếu Nam Vang là một món ăn đặc trưng của Việt Nam, với hương vị đậm đà và phong cách riêng biệt. Dưới đây là mô tả chi tiết về món ăn này:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Nguyên liệu chính: Hủ tiếu Nam Vang chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, trứng cút, tỏi, hành lá, cần tàu, xà lách, giá và hủ tiếu1.\\n\" +\n" +
                        "\"\\t\\t+ Hương vị: Hủ tiếu Nam Vang có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\\n\" +\n" +
                        "\"\\t\\t+ Cách chế biến: Hủ tiếu Nam Vang được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\\n\" +\n" +
                        "\"\\t\\t+ Phục vụ: Hủ tiếu Nam Vang thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\\n\" +\n" +
                        "\"\\t\\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1.", 6));
        cosmeticList.add(new Cosmetic(1, "Mì vằn thắn", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mi_vanthan, null)),
                "Mì vằn thắn là một món ăn thơm ngon, bổ dưỡng và cực kỳ hấp dẫn. Dưới đây là cách chế biến món mì vằn thắn:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Mì vằn thắn chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, tôm khô, mì tươi, vỏ gói sủi cảo, bóng bì lợn, trứng gà, rau tần ô hoặc rau cải xanh123.\n" +
                        "\t\t+ Hương vị: Mì vằn thắn có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài2.\n" +
                        "\t\t+ Cách chế biến: Mì vằn thắn được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Mì vằn thắn thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng", 6));
        cosmeticList.add(new Cosmetic(1, "Mì xá xíu", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mi_xaxiu, null)),
                "Mì Xá Xíu là một món ăn ngon và thường được ăn vào bữa sáng. Dưới đây là cách chế biến món Mì Xá Xíu:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Mì Xá Xíu chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, tôm khô, mì tươi, vỏ gói sủi cảo, bóng bì lợn, trứng gà, rau tần ô hoặc rau cải xanh12.\n" +
                        "\t\t+ Hương vị: Mì Xá Xíu có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài3.\n" +
                        "\t\t+ Cách chế biến: Mì Xá Xíu được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ3.\n" +
                        "\t\t+ Phục vụ: Mì Xá Xíu thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị3.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng3", 6));
        cosmeticList.add(new Cosmetic(1, "Nui", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.nui, null)),
                "Nui là một loại mì Ý dạng ngắn, thường được sử dụng trong nhiều món ăn khác nhau. Dưới đây là một số cách chế biến nui:\n" +
                        "\n" +
                        "\t\t+ Nui xào hải sản: Món ăn này được làm từ rau mực cắt nhỏ, nui, tôm sú, sò điệp, thịt ngao, cà chua bằm, bắp hạt (Hà Lan), muối (tiêu, đường, ăn), cà chua hộp (bằm, tỏi bằm), và hạt Aji-ngon Heo1.\n" +
                        "\t\t+ Nui Xào Lòng Gà: Món ăn này được làm từ lòng gà (mề, gan, tim), nui, hành lá (gia vị nem nem), và cải ngọt2.\n" +
                        "\t\t+ Nui sốt cay kiểu Hàn: Món ăn này được làm từ nui khô, quả trứng cút, lá rong biển, cá cơm (khô), tôm khô, lá (hẹ), và gia vị (Siro bắp, đường, nước tương, dầu ăn, ớt bột Hàn Quốc, tương ớt Hàn Quốc)3", 6));
        cosmeticList.add(new Cosmetic(1, "Phở bò", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.phobo, null)),
                "Phở Bò là một món ăn quốc gia của Việt Nam, một món ăn mà nhiều người Việt yêu thích. Dưới đây là cách chế biến Phở Bò:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Phở Bò chủ yếu được làm từ xương cục (xương ống), thịt bò, hành khô, gừng, quế, hoa hồi, trái thảo (quả), muối, hạt nêm, nước mắm, chín (mì, bột ngọt), hành lá (rau mùi, ngò ri), bánh phở1.\n" +
                        "\t\t+ Hương vị: Phở Bò có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Phở Bò được chế biến bằng cách nấu sôi các nguyên liệu như xương cục (xương ống), thịt bò trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Phở Bò thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng", 6));

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
            cosmeticSizeList.add(new CosmeticSize(i, 1, (random.nextInt(50) + 1) * 1000d));
            cosmeticSizeList.add(new CosmeticSize(i, 2, (random.nextInt(50) + 21) * 1000d));
            cosmeticSizeList.add(new CosmeticSize(i, 3, (random.nextInt(50) + 41) * 1000d));
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
