package com.hoangtien2k3.foody_order_app.repositoryInit;

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

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "foody_order_app_version_2.sqlite";
    private static final Integer DATABASE_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;
    private final Context context;

    // List Sample DataSS
    private List<User> userList;
    private List<Restaurant> restaurantList;
    private List<RestaurantSaved> restaurantSavedList;
    private List<Food> foodList;
    private List<FoodSize> foodSizeList;
    private List<Notify> notifyList;
    private List<NotifyToUser> notifyToUsers;
    private List<Order> orderList;
    private List<OrderDetail> orderDetailList;
    private List<FoodSaved> foodSavedList;

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
        userList.add(new User(1, "Hoàng Anh Tiến", "Male", "12-04-2003", "0828007853", "hoangtien2k3", "123456"));
        userList.add(new User(2, "Nguyễn Chí Hải Anh", "Male", "17-04-2003", "0947679750", "nguyenchihaianh", "123456"));
        userList.add(new User(3, "Vũ Mạnh Chiến", "Male", "25-06-2003", "0388891635", "vumanhchien", "123456"));

        // region Restaurant
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "Quán bánh mỳ", "Số 24/63 Phùng Khoang, Trung Văn, Nam Từ Liêm",
                "0631335935", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_banh_my_cho_phung_khoang, null))));
        restaurantList.add(new Restaurant(2, "Quán trà sữa", "Số 16/84 Hồ Tùng Mậu, Cầu Giấy, Hà Nội",
                "0885438847", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_cafe_duong_tau, null))));
        restaurantList.add(new Restaurant(3, "Quán cơm tấm", "Số 44/132 Q.Cầy Giấy, P.Quang Hoa, Hà Nội",
                "0559996574", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_com_tam_phuc_map, null))));
        restaurantList.add(new Restaurant(4, "Quán bánh Thanh Hà", "Số 184 Phùng Khoang, Trung Văn, Nam Từ Liêm",
                "0141670738", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_banh_ngot_le_van_viet, null))));
        restaurantList.add(new Restaurant(5, "Quán kem Moly", "105 Thanh Xuân Bắc, Q.Thanh Xuân, Hà Nôi",
                "0627724695", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_kem_co_hai, null))));
        restaurantList.add(new Restaurant(6, "Phở bò Hoàng Tiến", "Royal City 72A Nguyễn Trãi, Nam Từ Liêm",
                "0828007853", convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.quan_pho_gia_truyen, null))));

        // region Restaurant saved
        restaurantSavedList = new ArrayList<>();
        restaurantSavedList.add(new RestaurantSaved(1, 3));
        restaurantSavedList.add(new RestaurantSaved(4, 3));
        restaurantSavedList.add(new RestaurantSaved(1, 1));
        restaurantSavedList.add(new RestaurantSaved(1, 2));
        restaurantSavedList.add(new RestaurantSaved(2, 2));
        restaurantSavedList.add(new RestaurantSaved(6, 3));

        // region Food
        foodList = new ArrayList<>();
        // region Kem
        foodList.add(new Food(1, "Kem hộp đậu đỏ", "Kem",
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
        foodList.add(new Food(1, "Kem hộp sữa dừa", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemhop_suadua, null)),
                "Kem hộp sữa dừa là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của sữa dừa. \n\n- Dưới đây là một số thông tin về các loại kem hộp sữa dừa phổ biến:\n" +
                        "\t\t+ Kem hộp sữa dừa Merino 900ml: Sản phẩm này kết hợp độc đáo giữa sầu riêng tự nhiên và sữa dừa mềm béo thơm bùi khó cưỡng, vị ngon hoàn hảo1. Giá bán tại Bách hóa XANH khoảng 84.000 VND.\n" +
                        "\t\t+ Kem ly sữa dừa Merino 53g: Giá bán khoảng 13.000 VND, kem vẫn giữ nguyên nét thơm ngon đặc trưng truyền thống, cực hợp khẩu vị của người Việt, mang đến hương vị độc đáo cực ngon miệng.\n" +
                        "\t\t+ Hộp sữa dừa 450ml - Kem Merino: Kem hộp Merino thơm ngon với các hương vị truyền thống quen thuộc luôn là món tráng miệng tuyệt vời cho cả gia đình.", 5));
        foodList.add(new Food(1, "Kem ốc quế vani", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_vani, null)),
                "Kem ốc quế vani là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của vani. \n\n- Dưới đây là một số thông tin về các loại kem ốc quế vani phổ biến:\n" +
                        "\t\t+ Kem ốc quế vani socola Celano 110ml: Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sô cô la ngọt ngào, vừa đậm đà đầy kích thích1. Giá bán tại Bách hóa XANH khoảng 20.500 VND1.\n" +
                        "\t\t+ Kem Ốc Quế Crunchy Classic Vani 70g: Sản phẩm này có vị vani hấp dẫn cùng phần ốc quế làm từ bánh quy giòn thơm hòa quyện với cái lạnh mát của kem mang lại bạn sự ngọt ngào khó tả không thể bỏ qua2. Giá bán khoảng 19.000 VND2.\n" +
                        "\t\t+ Kem ốc quế Merino Superteen vị vani socola 60g: Sản phẩm này được sản xuất từ nguồn nguyên liệu tươi ngon, đảm bảo chất lượng. Vị kem mát lạnh, kết hợp với vị ngọt dịu của dâu và hương vani thơm mát, mang lại sự ngon miệng, sảng khoái cho người thưởng thức3.\n" +
                        "\t\t+ Kem vani socola Merino Super Teen cây 60g: Sản phẩm này là dạng kem cây ốc quế vô cùng thơm ngon và tiện dụng với hương vị vani socola hòa quyện độc đáo4.", 5));
        foodList.add(new Food(1, "Kem ôc quế dâu", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem ốc quế dâu là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của dâu. \n\n-  Dưới đây là một số thông tin về các loại kem ốc quế dâu phổ biến:\n" +
                        "\t\t+ Kem ốc quế dâu Celano Extra cây 125ml (73g): Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sữa kem béo nhẹ & mứt dâu1. Giá bán tại Bách hóa XANH khoảng 24.000 VND1.\n" +
                        "\t\t+ Kem ốc quế vani dâu Celano cây 66g: Sản phẩm này có vị thơm ngon khó cưỡng, giúp hạ nhiệt, giải khát vô cùng hiệu quả cho các ngày nắng nóng2. Giá bán khoảng 20.000 VND2.\n" +
                        "\t\t+ Kem Ốc Quế Delight Dâu - Nam Việt Quất 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn, thơm lừng mang đến cho bạn cảm giác thật mát lạnh & ngon khó cưỡng ngay từ miếng cắn đầu tiên3", 5));
        foodList.add(new Food(1, "Kem ốc quế socola", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Kem ốc quế socola là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của socola. \n\n- Dưới đây là một số thông tin về các loại kem ốc quế socola phổ biến:\n" +
                        "\t\t+ Kem ốc quế socola King’s cây 80g: Sản phẩm này với các nguyên liệu tự nhiên cao cấp, không sử dụng các hóa chất độc hại. Kem ốc quế socola King’s cây 80g vị socola đậm đà với thiết kế ấn tượng, bắt mắt, hương vị thơm béo càng làm cho sản phẩm thêm phần hấp dẫn1.\n" +
                        "\t\t+ Kem ốc quế socola extra Celano cây 75g: Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sô cô la extra đầy độc đáo, béo thơm, vừa đậm đà đầy kích thích2.\n" +
                        "\t\t+ Kem ốc quế Merino Superteen vị vani socola 60g: Sản phẩm này được sản xuất từ nguồn nguyên liệu tươi ngon, đảm bảo chất lượng. Vị kem mát lạnh, kết hợp với vị ngọt dịu của dâu và hương vani thơm mát, mang lại sự ngon miệng, sảng khoái cho người thưởng thức3.\n" +
                        "\t\t+ Kem Ốc Quế TH true ICE CREAM Sô Cô La Nguyên Chất: Sản phẩm được làm từ sữa tươi sạch nguyên chất của Trang trại TH cùng các nguyên liệu hoàn toàn tự nhiên. Sản phẩm có lớp kem sô cô la nguyên chất mềm mịn, được phủ sốt sô cô la cùng hạt đậu phộng thơm ngậy trên bề mặt, cuộn trong vỏ bánh ốc quế giòn tan bên ngoài4.\n" +
                        "\t\t+ Kem Ốc Quế Delight Sôcôla - Đậu Phộng 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn", 5));
        foodList.add(new Food(1, "Kem ôc quế dâu", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_dau, null)),
                "Kem ốc quế dâu là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của dâu. \\n\\n-  Dưới đây là một số thông tin về các loại kem ốc quế dâu phổ biến:\\n\" +\n" +
                        "\"\\t\\t+ Kem ốc quế dâu Celano Extra cây 125ml (73g): Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sữa kem béo nhẹ & mứt dâu1. Giá bán tại Bách hóa XANH khoảng 24.000 VND1.\\n\" +\n" +
                        "\"\\t\\t+ Kem ốc quế vani dâu Celano cây 66g: Sản phẩm này có vị thơm ngon khó cưỡng, giúp hạ nhiệt, giải khát vô cùng hiệu quả cho các ngày nắng nóng2. Giá bán khoảng 20.000 VND2.\\n\" +\n" +
                        "\"\\t\\t+ Kem Ốc Quế Delight Dâu - Nam Việt Quất 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn, thơm lừng mang đến cho bạn cảm giác thật mát lạnh & ngon khó cưỡng ngay từ miếng cắn đầu tiên3", 5));
        foodList.add(new Food(1, "Kem ốc quế socola", "Kem",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.kemocque_socola, null)),
                "Kem ốc quế socola là một loại kem thơm ngon, mềm mịn, có hương vị đặc trưng của socola. \n\n- Dưới đây là một số thông tin về các loại kem ốc quế socola phổ biến:\n" +
                        "\t\t+ Kem ốc quế socola King’s cây 80g: Sản phẩm này với các nguyên liệu tự nhiên cao cấp, không sử dụng các hóa chất độc hại. Kem ốc quế socola King’s cây 80g vị socola đậm đà với thiết kế ấn tượng, bắt mắt, hương vị thơm béo càng làm cho sản phẩm thêm phần hấp dẫn.\n" +
                        "\t\t+ Kem ốc quế socola extra Celano cây 75g: Sản phẩm này mang đến sự độc đáo, mới lạ với kem bánh giòn cùng vị sô cô la extra đầy độc đáo, béo thơm, vừa đậm đà đầy kích thích.\n" +
                        "\t\t+ Kem ốc quế Merino Superteen vị vani socola 60g: Sản phẩm này được sản xuất từ nguồn nguyên liệu tươi ngon, đảm bảo chất lượng. Vị kem mát lạnh, kết hợp với vị ngọt dịu của dâu và hương vani thơm mát, mang lại sự ngon miệng, sảng khoái cho người thưởng thức.\n" +
                        "\t\t+ Kem Ốc Quế TH true ICE CREAM Sô Cô La Nguyên Chất: Sản phẩm được làm từ sữa tươi sạch nguyên chất của Trang trại TH cùng các nguyên liệu hoàn toàn tự nhiên. Sản phẩm có lớp kem sô cô la nguyên chất mềm mịn, được phủ sốt sô cô la cùng hạt đậu phộng thơm ngậy trên bề mặt, cuộn trong vỏ bánh ốc quế giòn tan bên ngoài.\n" +
                        "\t\t+ Kem Ốc Quế Delight Sôcôla - Đậu Phộng 110ml: Là sự hòa quyện độc đáo giữa vị béo kem sữa cùng những nguyên liệu thơm ngon, phủ bên trên chiếc bánh ốc quế giòn giòn.", 5));

        // region Banh mi
        foodList.add(new Food(1, "Bánh mì bò kho", "Bánh mì",
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
        foodList.add(new Food(1, "Bánh mì bơ tỏi", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_botoi, null)),
                "Bánh mì bơ tỏi là một món ăn rất nhiều người yêu thích, không chỉ tiện lợi cho bữa sáng dinh dưỡng mà còn cực kỳ thích hợp cho những buổi tiệc nhỏ1. \n\n-  Dưới đây là một số công thức nấu bánh mì bơ tỏi:\n" +
                        "\t\t+ Hướng Dẫn Cách Làm Bánh Mì Bơ Tỏi Siêu Ngon - Huongnghiepaau1: Công thức này giới thiệu cách làm bánh mì bơ tỏi kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh mì bơ tỏi giòn thơm nức mũi - Bách hóa XANH2: Công thức này hướng dẫn cách làm bánh mì bơ tỏi thơm ngon, giòn rụm đơn giản tại nhà2.\n" +
                        "\t\t+ Cách làm bánh mì bơ tỏi bằng nồi chiên không dầu hấp dẫn3: Công thức này hướng dẫn cách làm bánh mì bơ tỏi với nồi chiên không dầu, giúp bạn có được chiếc bánh mì giòn rụm, thơm phức3.", 1));
        foodList.add(new Food(1, "Bánh mì chảo", "Bánh mì",
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
        foodList.add(new Food(1, "Bánh mì hoa cúc", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_hoacuc, null)),
                "Bánh mì chảo là một món ăn ngon được giới trẻ yêu thích và ngày càng trở nên phổ biến1. Hương vị hòa quyện giữa xúc xích, trứng, pate, thịt bò mềm… đắm chìm trong nước xốt đậm đà, sánh mịn thật khó quên khi đã thưởng thức qua 1 lần1. \n\n- Dưới đây là một số công thức nấu bánh mì chảo:\n" +
                        "\t\t+ Cách làm bánh mì chảo pate, xúc xích1: Công thức này giới thiệu cách làm bánh mì chảo kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Tự Tay Làm Bánh Mì Chảo Cho Siêu Ngon Bữa Sáng Đầy Năng Lượng2: Công thức này hướng dẫn cách làm bánh mì chảo thơm ngon, giòn rụm đơn giản tại nhà2.\n" +
                        "\t\t+ Bánh mì chảo cá hộp3: Bạn tìm 1 món ăn sáng chế biến nhanh nhưng cũng phải ngon miệng thì bánh mì chảo là món bạn đang tìm kiếm. Nguyên liệu dễ mua, thời gian thực hiện nhanh chóng và còn rất được lòng của mọi người3.\n" +
                        "\t\t- 4 cách làm bánh mì chảo tại nhà thơm ngon cho bữa sáng4: Công thức này hướng dẫn cách làm bánh mì chảo theo công thức như thế nào ngon nhất. Vì thế, hôm nay Chef.vn sẽ hướng dẫn các bạn cách nấu bò kho bánh mì rất đơn giản mà lại dễ làm, quan trọng nhất là bạn phải biết mẹo để ướp thịt bò để món bò kho đậm đà thơm ngon mà không bị dai", 1));
        foodList.add(new Food(1, "Bánh mì hoa cúc", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_hoacuc, null)),
                "Bánh mì hoa cúc, còn được gọi là Brioche, là một loại bánh mì có nguồn gốc từ Pháp. Với thành phần giàu bơ và trứng, bánh mì hoa cúc có một lớp vỏ mềm, ẩm và vàng sẫm. Khi nướng lên, bánh luôn có thớ mềm và xốp, hương vị vô cùng tuyệt vời1. \n\n- Dưới đây là một số công thức nấu bánh mì hoa cúc:\n" +
                        "\t\t+ Cách Làm Bánh Mì Hoa Cúc Đơn Giản & Ngon Nhất1: Công thức này giới thiệu cách làm bánh mì hoa cúc kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh mì hoa cúc Harrys (đơn giản, nhồi bột dễ) [VIDEO]2: Công thức này hướng dẫn cách làm bánh mì hoa cúc theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh mì hoa cúc Harrys (đơn giản, nhồi bột dễ) [VIDEO] - Savoury Days2: Công thức này hướng dẫn cách làm bánh mì hoa cúc theo công thức như thế nào ngon nhất2", 1));
        foodList.add(new Food(1, "Bánh mì kẹp thịt", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_kepthit, null)),
                "Bánh mì kẹp thịt là một món ăn truyền thống của người Việt, được nhiều người yêu thích và trở thành “siêu sao” ẩm thực không chỉ ở Việt Nam mà còn trên khắp thế giới1. \n\n- Dưới đây là một số công thức nấu bánh mì kẹp thịt:\n" +
                        "\t\t+ Cách Làm Bánh Mì Kẹp Thịt Việt Nam Làm Mê Mẩn Thực Khách Thế Giới - Huongnghiepaau1: Công thức này giới thiệu cách làm bánh mì kẹp thịt kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm 4 món bánh mì kẹp siêu ngon và đơn giản - YummyDay2: Công thức này hướng dẫn cách làm bánh mì kẹp thơm ngon, giòn rụm đơn giản tại nhà2.\n" +
                        "\t\t+ Bánh mì sandwich kẹp thịt nguội cho bữa sáng ngon lành3: Công thức này hướng dẫn cách làm bánh mì sandwich kẹp thịt nguội cho bữa sáng ngon lành3.\n" +
                        "\t\t+ Top 10 loại nhân kẹp bánh mì thơm ngon, dinh dưỡng cho bữa sáng4: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất4.\n" +
                        "\t\t+ Cách làm bánh mì kẹp thịt ngon không kém ngoài hàng5: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất5.", 1));
        foodList.add(new Food(1, "Bánh mì kẹp xúc xích", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhmi_kepxucxich, null)),
                "Bánh mì kẹp xúc xích là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. \n\n- Dưới đây là một số công thức nấu bánh mì kẹp xúc xích:\n" +
                        "\t\t+ Cách làm bánh mì kẹp trứng xúc xích cho bữa sáng ngon miệng!1: Công thức này giới thiệu cách làm bánh mì kẹp trứng xúc xích kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh mì kẹp trứng xúc xích CỰC NGON2: Công thức này hướng dẫn cách làm bánh mì kẹp trứng xúc xích thơm ngon hấp dẫn chi tiết2.\n" +
                        "\t\t+ Cách làm bánh mì kẹp xúc xích NGON, RẺ, CỰC DỄ LÀM tại nhà3: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất3.\n" +
                        "\t\t+ Bật mí cách làm bánh mì kẹp xúc xích đơn giản, chuẩn vị4: Công thức này hướng dẫn cách làm bánh mì kẹp theo công thức như thế nào ngon nhất4.", 1));
        foodList.add(new Food(1, "Hamburger bò", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Hamburger bò là một món ăn nhanh phổ biến của người phương Tây1. \n\n- Dưới đây là một số công thức nấu Hamburger bò:\n" +
                        "\t\t+ Cách làm Hamburger bò cực đơn giản chỉ trong 3 bước - PasGo2: Công thức này giới thiệu cách làm Hamburger bò kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu2.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng1: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));
        foodList.add(new Food(1, "Hamburger heo", "Bánh mì",
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
        foodList.add(new Food(1, "Hamburger bò", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_bo, null)),
                "Hamburger bò là một món ăn nhanh phổ biến của người phương Tây1. \n\n- Dưới đây là một số công thức nấu Hamburger bò:\n" +
                        "\t\t+ Cách làm Hamburger bò cực đơn giản chỉ trong 3 bước - PasGo2: Công thức này giới thiệu cách làm Hamburger bò kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu2.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng1: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));
        foodList.add(new Food(1, "Hamburger heo", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_heo, null)),
                "Hamburger heo là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. \n\n- Dưới đây là một số công thức nấu Hamburger heo:\n" +
                        "\t\t+ Cách làm Hamburger heo cực đơn giản chỉ trong 3 bước - PasGo1: Công thức này giới thiệu cách làm Hamburger heo kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng2: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));
        foodList.add(new Food(1, "Hamburger gà", "Bánh mì",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.burger_ga, null)),
                "Hamburger gà là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. \n\n- Dưới đây là một số công thức nấu Hamburger gà:\n" +
                        "\t\t+ Cách làm Hamburger gà cực đơn giản chỉ trong 3 bước - PasGo1: Công thức này giới thiệu cách làm Hamburger gà kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ 3 cách làm hamburger bò kiểu Mỹ ngon như ngoài hàng2: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ 2 Cách Làm Hamburger Bò Cực Ngon Không Thể Bỏ Qua3: Công thức này hướng dẫn cách làm hamburger bò theo công thức như thế nào ngon nhất", 1));

        // region Banh ngot
        foodList.add(new Food(1, "Bánh đậu xanh cốt dừa", "Bánh ngọt",
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
        foodList.add(new Food(1, "Bánh matcha", "Bánh ngọt",
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
        foodList.add(new Food(1, "Bánh sầu riêng", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "Bánh matcha là một loại bánh ngọt có hương vị đặc trưng của bột trà xanh matcha. Dưới đây là một số công thức làm bánh matcha:\n" +
                        "\n" +
                        "\t\t+ Bánh bông lan matcha: Bánh bông lan matcha có vị béo ngọt, đắng nhẹ của bột trà xanh và hương vị thơm ngon của trứng. Bánh bông lan matcha mềm mịn, thơm ngon chắc chắn sẽ làm các bé thích mê1.\n" +
                        "\t\t+ Bánh mousse matcha: Bánh mousse matcha là sự kết hợp cực kỳ sáng tạo và mới lạ từ những nguyên liệu đơn giản. Bột trà xanh đắng nhẹ, thơm dịu kết hợp cùng whipping cream béo ngậy chắc chắn sẽ khiến cho những tâm hồn yêu đồ ngọt thích thú1.\n" +
                        "\t\t+ Bánh Chocopie Orion Matcha Trà Xanh: Lớp bánh xốp mịn với sô cô la chảy ngọt ngào bên ngoài đến lớp nhân marshmallow vị matcha thanh hấp dẫn ở bên trong2.", 4));
        foodList.add(new Food(1, "Bánh bông lan cuộn", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "Bánh bông lan cuộn là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan cuộn:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn đơn giản1: Công thức này giới thiệu cách làm bánh bông lan cuộn kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn Vanilla Cake Roll Recipe1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn kem tươi hoa quả2: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn hoa cúc1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1", 4));
        foodList.add(new Food(1, "Bánh sầu riêng", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banh_saurieng, null)),
                "Bánh sầu riêng là một loại bánh ngọt có hương vị đặc trưng của sầu riêng. Dưới đây là một số thông tin về các loại bánh sầu riêng phổ biến:\n" +
                        "\n" +
                        "\t\t+ Bánh Sầu Riêng TBT: Bánh được làm bằng sầu riêng Ri6 tươi nguyên chất, không sử dụng sầu kem sầu pha trộn. Vỏ bánh dai mềm mịn, không bị bột bở khi ăn. Lớp kem lạnh thơm béo, xen lẫn nhân sầu ngọt dịu1.\n" +
                        "\t\t+ Bánh Sầu Riêng: Chuyên sỉ & lẻ bánh sầu riêng ngàn lớp, bánh crepe. Với phương châm luôn đặt chất lượng lên hàng đầu, chúng tôi tự hào mang đến những sản phẩm tốt nhất cho khách hàng2.\n" +
                        "\t\t+ Bánh Sầu Riêng Chiên: Bánh Sầu Riêng Chiên giòn rụm thơm ngon tuyệt cú mèo3.\n" +
                        "\t\t+ Bánh Trung Thu Nhân Sầu Riêng: Bánh Trung Thu Nhân Sầu Riêng thơm nức mũi4", 4));
        foodList.add(new Food(1, "Bánh bông lan cuộn", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_cuon, null)),
                "Bánh bông lan cuộn là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan cuộn:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn đơn giản1: Công thức này giới thiệu cách làm bánh bông lan cuộn kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn Vanilla Cake Roll Recipe1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn kem tươi hoa quả2: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh bông lan cuộn hoa cúc1: Công thức này hướng dẫn cách làm bánh bông lan cuộn theo công thức như thế nào ngon nhất1", 4));
        foodList.add(new Food(1, "Bánh bông lan socola", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_socola, null)),
                "Bánh bông lan socola là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan socola:\n" +
                        "\n" +
                        "\t\t+ Cách làm Bánh Bông Lan Socola cực đơn giản chỉ trong 3 bước - PasGo1: Công thức này giới thiệu cách làm Bánh Bông Lan Socola kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách làm Bánh Bông Lan Socola Tươi | Chocolate Cake | Chị Mía2: Công thức này hướng dẫn cách làm Bánh Bông Lan Socola Tươi theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Cách làm bánh bông lan socola cực đơn giản (chocolate sponge cake …3: Công thức này hướng dẫn cách làm bánh bông lan socola theo công thức như thế nào ngon nhất3.\n" +
                        "\t\t+ BÁNH BÔNG LAN SOCOLA – Công ty TNHH Đại Hiền Tâm4: Công thức này hướng dẫn cách làm bánh bông lan socola theo công thức như thế nào ngon nhất4", 4));
        foodList.add(new Food(1, "Bánh bông lan trứng muối", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "Bánh bông lan trứng muối là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan trứng muối:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan trứng muối (bằng nồi cơm điện hoặc lò nướng)1: Công thức này giới thiệu cách làm bánh bông lan trứng muối kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách Làm Bánh Bông Lan Trứng Muối Đơn Giản Tại Nhà2: Công thức này hướng dẫn cách làm bánh bông lan trứng muối theo công thức như thế nào ngon nhất", 4));
        foodList.add(new Food(1, "Bánh bông lan trứng muối", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhbonglan_trungmuoi, null)),
                "Bánh bông lan trứng muối là một món ăn ngon và phổ biến, thường được dùng làm bữa sáng hoặc bữa ăn nhẹ. Dưới đây là một số công thức nấu Bánh bông lan trứng muối:\n" +
                        "\n" +
                        "\t\t+ Cách làm bánh bông lan trứng muối (bằng nồi cơm điện hoặc lò nướng)1: Công thức này giới thiệu cách làm bánh bông lan trứng muối kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Cách Làm Bánh Bông Lan Trứng Muối Đơn Giản Tại Nhà2: Công thức này hướng dẫn cách làm bánh bông lan trứng muối theo công thức như thế nào ngon nhất", 4));
        foodList.add(new Food(1, "Bánh su kem", "Bánh ngọt",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.banhsukem, null)),
                "Bánh su kem, còn được gọi là Choux à la crème, là một loại bánh ngọt có nguồn gốc từ nước Pháp. Bánh su kem có độ mềm dẻo, thơm ngậy đầy lôi cuốn1. Dưới đây là một số công thức nấu bánh su kem:\n" +
                        "\n" +
                        "\t\t+ Cách làm Bánh Su Kem (Choux à la crème) Ngon Béo Ngậy – bTaskee1: Công thức này giới thiệu cách làm bánh su kem kiểu mới làm món ăn “đổi gió” cho cả gia đình. Cách làm khá đơn giản và dễ dàng, bất cứ ai cũng có thể thành công ngay lần đầu1.\n" +
                        "\t\t+ Làm Bánh Su Kem Với Công Thức Dễ Thành Công Ngay Lần Đầu | Choux Pastry Recipe | Chị Mía2: Công thức này hướng dẫn cách làm bánh su kem theo công thức như thế nào ngon nhất2.\n" +
                        "\t\t+ Bánh Su kem (Choux à la Crème) – Demo & Một số lưu ý khi làm Choux3: Công thức này hướng dẫn cách làm bánh su kem theo công thức như thế nào ngon nhất", 4));

        // region Com suon
        foodList.add(new Food(1, "Cơm sườn trứng", "Cơm sườn",
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
        foodList.add(new Food(1, "Cơm sườn bì chả", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "Cơm sườn bì chả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm cơm tấm (cơm gạo tấm), sườn heo nướng, bì (da heo giòn) và chả (thường là chả trứng hoặc chả hấp). " +
                        "\n+ Cơm tấm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng. Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới. \n+ " +
                        "\t\t+ Bì được làm từ da heo, thường được luộc và xắt nhỏ. Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người. \n+ " +
                        "\t\t+ Món ăn thường được dùng kèm với nước mắm pha chua ngọt và rau sống. Cơm sườn bì chả là một món ăn ngon và bổ dưỡng, rất phù hợp cho bữa trưa hoặc bữa tối. \n+ " +
                        "\t\t+ Chúc bạn thưởng thức ngon miệng! \uD83D\uDE0A", 3));
        foodList.add(new Food(1, "Cơm sườn nướng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "Cơm sườn nướng ả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm:\n" +
                        "\n" +
                        "\t\t+ Cơm tấm: Cơm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng.\n" +
                        "\t\t+ Sườn heo nướng: Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới.\n" +
                        "\t\t+ Bì (da heo giòn): Bì được làm từ da heo, thường được luộc và xắt nhỏ.\n" +
                        "\t\t+ Chả: Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người.", 3));
        foodList.add(new Food(1, "Cơm sườn ram", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "Cơm sườn ram là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam. Món này bao gồm cơm trắng được phục vụ cùng với sườn heo đã được ướp với các loại gia vị và sau đó được ram cho tới khi chín tới. Dưới đây là một số công thức nấu Cơm sườn ram:\n" +
                        "\n" +
                        "\t\t+ Cách làm món sườn ram: 6 công thức ram sườn ngon khỏi chê1: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, ớt, đường, bột nêm, bột ngọt, nước mắm, nước màu trong khoảng 20 phút. Sau đó, sườn được chiên trong chảo dầu nóng. Khi sườn chín, lửa được tăng lớn để sườn lên màu1.\n" +
                        "\t\t+ Cách Làm Sườn Ram Mặn Ngọt Ngon Mềm, Đậm Đà Đưa Cơm2: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, tỏi, nước mắm, dầu hào, bột nêm, tiêu và nước hàng. Sau đó, sườn được chiên trong chảo dầu nóng. Khi miếng sườn đã rất mềm và thấm gia vị mặn ngọt đậm đà, thêm hành lá vào chảo và tắt bếp2.", 3));
        foodList.add(new Food(1, "Cơm sườn bì chả", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_bi_cha, null)),
                "Cơm sườn bì chả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm cơm tấm (cơm gạo tấm), sườn heo nướng, bì (da heo giòn) và chả (thường là chả trứng hoặc chả hấp). \\n+ Cơm tấm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng. Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới. \\n+ \" +\n" +
                        "\"Bì được làm từ da heo, thường được luộc và xắt nhỏ. Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người. \\n+ \" +\n" +
                        "\"Món ăn thường được dùng kèm với nước mắm pha chua ngọt và rau sống. Cơm sườn bì chả là một món ăn ngon và bổ dưỡng, rất phù hợp cho bữa trưa hoặc bữa tối. \\n+ \" +\n" +
                        "\"Chúc bạn thưởng thức ngon miệng! \\uD83D\\uDE0A", 3));
        foodList.add(new Food(1, "Cơm sườn nướng", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_nuong, null)),
                "Cơm sườn nướng ả là một món ăn truyền thống của người Việt, đặc biệt là ở miền Nam. Món này bao gồm:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Cơm tấm: Cơm được nấu từ gạo tấm, loại gạo vụn vỡ nhỏ, mang lại hương vị đặc trưng.\\n\" +\n" +
                        "\"\\t\\t+ Sườn heo nướng: Sườn heo được ướp với các loại gia vị như nước mắm, đường, tỏi… sau đó được nướng cho tới khi chín tới.\\n\" +\n" +
                        "\"\\t\\t+ Bì (da heo giòn): Bì được làm từ da heo, thường được luộc và xắt nhỏ.\\n\" +\n" +
                        "\"\\t\\t+ Chả: Chả có thể là chả trứng hoặc chả hấp, tùy vào sở thích của từng người.", 3));
        foodList.add(new Food(1, "Cơm sườn ram", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_ram, null)),
                "Cơm sườn ram là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam. Món này bao gồm cơm trắng được phục vụ cùng với sườn heo đã được ướp với các loại gia vị và sau đó được ram cho tới khi chín tới. Dưới đây là một số công thức nấu Cơm sườn ram:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Cách làm món sườn ram: 6 công thức ram sườn ngon khỏi chê1: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, ớt, đường, bột nêm, bột ngọt, nước mắm, nước màu trong khoảng 20 phút. Sau đó, sườn được chiên trong chảo dầu nóng. Khi sườn chín, lửa được tăng lớn để sườn lên màu1.\\n\" +\n" +
                        "\"\\t\\t+ Cách Làm Sườn Ram Mặn Ngọt Ngon Mềm, Đậm Đà Đưa Cơm2: Công thức này giới thiệu cách làm sườn ram mặn ngọt. Sườn non được ướp với hành tím, tỏi, nước mắm, dầu hào, bột nêm, tiêu và nước hàng. Sau đó, sườn được chiên trong chảo dầu nóng. Khi miếng sườn đã rất mềm và thấm gia vị mặn ngọt đậm đà, thêm hành lá vào chảo và tắt bếp2.", 3));
        foodList.add(new Food(1, "Cơm sườn xào chua ngọt", "Cơm sườn",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.comsuon_chuangot, null)),
                "Cơm sườn xào chua ngọt là một món ăn truyền thống của Việt Nam, đặc biệt phổ biến ở miền Nam. Món này bao gồm cơm trắng được phục vụ cùng với sườn heo đã được ướp với các loại gia vị và sau đó được xào chung với nước sốt chua ngọt. Dưới đây là một số công thức nấu Cơm sườn xào chua ngọt:\n" +
                        "\n" +
                        "\t\t+ 6 Cách Làm Sườn Xào Chua Ngọt Đậm Đà, Hấp Dẫn, Ngon Đúng Điệu1: Công thức này giới thiệu cách làm sườn xào chua ngọt mặn ngọt. Sườn non được ướp với hành tím, ớt, đường, bột nêm, bột ngọt, nước mắm, nước màu trong khoảng 20 phút. Sau đó, sườn được chiên trong chảo dầu nóng. Khi sườn chín, lửa được tăng lớn để sườn lên màu1.\n" +
                        "\t\t+ Cách làm sườn xào chua ngọt ngon bất bại, vô cùng đơn giản2: Công thức này giới thiệu cách làm sườn xào chua ngọt. Sườn non được ướp với hành tím, tỏi, nước mắm, dầu hào, bột nêm, tiêu và nước hàng. Sau đó, sườn được chiên trong chảo dầu nóng. Khi miếng sườn đã rất mềm và thấm gia vị mặn ngọt thì thêm hành lá vào chảo và tắt bếp2.\n" +
                        "\t\t+ Cách làm sườn xào chua ngọt kiểu miền Nam vét sạch nồi cơm3: Công thức này hướng dẫn cách làm sườn xào chua ngọt theo kiểu miền Nam3.\n" +
                        "\t\t+ Sườn xào chua ngọt cho bữa cơm ngon hấp dẫn mỗi ngày4: Công thức này hướng dẫn cách làm sườn xào chua ngọt4.", 3));

        // region Mon nuoc
        foodList.add(new Food(1, "Bánh canh", "Món nước",
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
        foodList.add(new Food(1, "Bún mắm", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bunmam, null)),
                "Bún mắm là một món ăn đặc trưng của Việt Nam, đặc biệt là miền Tây, với hương vị đậm đà và phong cách riêng biệt. Dưới đây là mô tả chi tiết về món ăn này:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu chính: Bún mắm chủ yếu được làm từ cá lóc phi lê, thịt heo quay, mắm linh, sặc (mắm cá), tôm sú, dưa, cà tím, sả cay, sả bằm và nước thơm1.\n" +
                        "\t\t+ Hương vị: Bún mắm có hương vị đậm đà từ nước lèo thơm nồng kết hợp với mùi nắm cá linh và cá sặc2. Món ăn này còn được bổ sung thêm hương vị từ các loại rau đặc trưng của miền Tây2.\n" +
                        "\t\t+ Cách chế biến: Bún mắm được chế biến bằng cách hấp các nguyên liệu chính như cá lóc phi lê, thịt heo quay và tôm sú. Sau đó, các nguyên liệu này được kết hợp với nước lèo thơm nồng để tạo ra hương vị đặc trưng cho món ăn1.\n" +
                        "\t\t+ Phục vụ: Bún mắm thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 9986 đánh giá1.", 6));
        foodList.add(new Food(1, "Bún thái", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "Bún Thái là một món ăn đặc trưng của Thái Lan, với hương vị chua cay đặc trưng. Dưới đây là cách chế biến món Bún Thái:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Bún Thái chủ yếu được làm từ thịt bò fillet mềm, râu mực, tôm sú tươi, nghêu to, rau muống, cà chua, nấm đông cô tươi, gừng cắt sợi, riềng cắt sợi và hành tím1.\n" +
                        "\t\t+ Hương vị: Bún Thái có hương vị chua chua, cay cay đặc trưng của xứ Thái2. Hương vị này được tạo ra từ việc kết hợp các loại gia vị đặc trưng trong ẩm thực Thái2.\n" +
                        "\t\t+ Cách chế biến: Bún Thái được chế biến bằng cách xào thơm các nguyên liệu như thịt bò, râu mực và tôm sú. Sau đó, các nguyên liệu này được kết hợp với nước dùng thơm nồng để tạo ra hương vị đặc trưng cho món ăn1.\n" +
                        "\t\t+ Phục vụ: Bún Thái thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 21339 đánh giá1.", 6));
        foodList.add(new Food(1, "Hoành thánh", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "Hoành thánh là một món ăn đặc trưng của Trung Quốc và đã trở nên phổ biến ở Việt Nam. Dưới đây là cách chế biến món hoành thánh:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Hoành thánh chủ yếu được làm từ thịt nạc vai xay, thịt tôm xay, cà rốt, nấm hương, hành tây, tỏi, hành tím và hành lá1.\n" +
                        "\t\t+ Hương vị: Hoành thánh có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Hoành thánh được chế biến bằng cách nặn từng viên hoành thánh rồi nấu trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hoành thánh thường được phục vụ nóng hổi cùng với nước dùng thanh mát1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 1 đánh giá1.", 6));
        foodList.add(new Food(1, "Hủ tiếu bò kho", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Hủ tiếu bò kho là một món ăn ngon, đậm đà được nhiều người yêu thích. Dưới đây là cách chế biến món hủ tiếu bò kho:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Hủ tiếu bò kho chủ yếu được làm từ thịt bò nạm, hủ tiếu, giá, màu dầu điều, hạt nêm, đường, muối, ớt bột, tiêu, bột gia vị bò kho, sả, ngò gai, hành lá, hành tím, hành tây, ớt, tỏi, quế, chanh1.\n" +
                        "\t\t+ Hương vị: Hủ tiếu bò kho có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Hủ tiếu bò kho được chế biến bằng cách ướp thịt bò trong 30 phút với gia vị bò kho, màu dầu điều, đường, hạt nêm, tiêu, ớt bột và muối. Sau đó thịt được xào sơ trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hủ tiếu bò kho thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1", 6));
        foodList.add(new Food(1, "Hủ tiếu nam vang", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Hủ tiếu Nam Vang là một món ăn đặc trưng của Việt Nam, với hương vị đậm đà và phong cách riêng biệt. Dưới đây là mô tả chi tiết về món ăn này:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu chính: Hủ tiếu Nam Vang chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, trứng cút, tỏi, hành lá, cần tàu, xà lách, giá và hủ tiếu1.\n" +
                        "\t\t+ Hương vị: Hủ tiếu Nam Vang có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Hủ tiếu Nam Vang được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hủ tiếu Nam Vang thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1.", 6));
        foodList.add(new Food(1, "Bún thái", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bun_thai, null)),
                "Bún Thái là một món ăn đặc trưng của Thái Lan, với hương vị chua cay đặc trưng. Dưới đây là cách chế biến món Bún Thái:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Bún Thái chủ yếu được làm từ thịt bò fillet mềm, râu mực, tôm sú tươi, nghêu to, rau muống, cà chua, nấm đông cô tươi, gừng cắt sợi, riềng cắt sợi và hành tím1.\n" +
                        "\t\t+ Hương vị: Bún Thái có hương vị chua chua, cay cay đặc trưng của xứ Thái2. Hương vị này được tạo ra từ việc kết hợp các loại gia vị đặc trưng trong ẩm thực Thái2.\n" +
                        "\t\t+ Cách chế biến: Bún Thái được chế biến bằng cách xào thơm các nguyên liệu như thịt bò, râu mực và tôm sú. Sau đó, các nguyên liệu này được kết hợp với nước dùng thơm nồng để tạo ra hương vị đặc trưng cho món ăn1.\n" +
                        "\t\t+ Phục vụ: Bún Thái thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 21339 đánh giá1", 6));
        foodList.add(new Food(1, "Hoành thánh", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hoanhthanh, null)),
                "Hoành thánh là một món ăn đặc trưng của Trung Quốc và đã trở nên phổ biến ở Việt Nam. Dưới đây là cách chế biến món hoành thánh:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Hoành thánh chủ yếu được làm từ thịt nạc vai xay, thịt tôm xay, cà rốt, nấm hương, hành tây, tỏi, hành tím và hành lá1.\n" +
                        "\t\t+ Hương vị: Hoành thánh có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+  Cách chế biến: Hoành thánh được chế biến bằng cách nặn từng viên hoành thánh rồi nấu trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Hoành thánh thường được phục vụ nóng hổi cùng với nước dùng thanh mát1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng, với điểm số trung bình là 5/5 dựa trên 1 đánh giá1.", 6));
        foodList.add(new Food(1, "Hủ tiếu bò kho", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_bokho, null)),
                "Hủ tiếu bò kho là một món ăn ngon, đậm đà được nhiều người yêu thích. Dưới đây là cách chế biến món hủ tiếu bò kho:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Nguyên liệu: Hủ tiếu bò kho chủ yếu được làm từ thịt bò nạm, hủ tiếu, giá, màu dầu điều, hạt nêm, đường, muối, ớt bột, tiêu, bột gia vị bò kho, sả, ngò gai, hành lá, hành tím, hành tây, ớt, tỏi, quế, chanh1.\\n\" +\n" +
                        "\"\\t\\t+ Hương vị: Hủ tiếu bò kho có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\\n\" +\n" +
                        "\"\\t\\t+ Cách chế biến: Hủ tiếu bò kho được chế biến bằng cách ướp thịt bò trong 30 phút với gia vị bò kho, màu dầu điều, đường, hạt nêm, tiêu, ớt bột và muối. Sau đó thịt được xào sơ trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\\n\" +\n" +
                        "\"\\t\\t+ Phục vụ: Hủ tiếu bò kho thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\\n\" +\n" +
                        "\"\\t\\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1", 6));
        foodList.add(new Food(1, "Hủ tiếu nam vang", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.hutieu_namvang, null)),
                "Hủ tiếu Nam Vang là một món ăn đặc trưng của Việt Nam, với hương vị đậm đà và phong cách riêng biệt. Dưới đây là mô tả chi tiết về món ăn này:\\n\" +\n" +
                        "\"\\n\" +\n" +
                        "\"\\t\\t+ Nguyên liệu chính: Hủ tiếu Nam Vang chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, trứng cút, tỏi, hành lá, cần tàu, xà lách, giá và hủ tiếu1.\\n\" +\n" +
                        "\"\\t\\t+ Hương vị: Hủ tiếu Nam Vang có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\\n\" +\n" +
                        "\"\\t\\t+ Cách chế biến: Hủ tiếu Nam Vang được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\\n\" +\n" +
                        "\"\\t\\t+ Phục vụ: Hủ tiếu Nam Vang thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\\n\" +\n" +
                        "\"\\t\\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng1.", 6));
        foodList.add(new Food(1, "Mì vằn thắn", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mi_vanthan, null)),
                "Mì vằn thắn là một món ăn thơm ngon, bổ dưỡng và cực kỳ hấp dẫn. Dưới đây là cách chế biến món mì vằn thắn:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Mì vằn thắn chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, tôm khô, mì tươi, vỏ gói sủi cảo, bóng bì lợn, trứng gà, rau tần ô hoặc rau cải xanh123.\n" +
                        "\t\t+ Hương vị: Mì vằn thắn có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài2.\n" +
                        "\t\t+ Cách chế biến: Mì vằn thắn được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Mì vằn thắn thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng", 6));
        foodList.add(new Food(1, "Mì xá xíu", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.mi_xaxiu, null)),
                "Mì Xá Xíu là một món ăn ngon và thường được ăn vào bữa sáng. Dưới đây là cách chế biến món Mì Xá Xíu:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Mì Xá Xíu chủ yếu được làm từ thịt nạc vai, tôm tươi, gan heo, tôm khô, mì tươi, vỏ gói sủi cảo, bóng bì lợn, trứng gà, rau tần ô hoặc rau cải xanh12.\n" +
                        "\t\t+ Hương vị: Mì Xá Xíu có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài3.\n" +
                        "\t\t+ Cách chế biến: Mì Xá Xíu được chế biến bằng cách nấu sôi các nguyên liệu như thịt nạc vai, gan heo và tôm trong nước dùng xương thơm vị đặc trưng của lá hẹ3.\n" +
                        "\t\t+ Phục vụ: Mì Xá Xíu thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị3.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng3", 6));
        foodList.add(new Food(1, "Nui", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.nui, null)),
                "Nui là một loại mì Ý dạng ngắn, thường được sử dụng trong nhiều món ăn khác nhau. Dưới đây là một số cách chế biến nui:\n" +
                        "\n" +
                        "\t\t+ Nui xào hải sản: Món ăn này được làm từ rau mực cắt nhỏ, nui, tôm sú, sò điệp, thịt ngao, cà chua bằm, bắp hạt (Hà Lan), muối (tiêu, đường, ăn), cà chua hộp (bằm, tỏi bằm), và hạt Aji-ngon Heo1.\n" +
                        "\t\t+ Nui Xào Lòng Gà: Món ăn này được làm từ lòng gà (mề, gan, tim), nui, hành lá (gia vị nem nem), và cải ngọt2.\n" +
                        "\t\t+ Nui sốt cay kiểu Hàn: Món ăn này được làm từ nui khô, quả trứng cút, lá rong biển, cá cơm (khô), tôm khô, lá (hẹ), và gia vị (Siro bắp, đường, nước tương, dầu ăn, ớt bột Hàn Quốc, tương ớt Hàn Quốc)3", 6));
        foodList.add(new Food(1, "Phở bò", "Món nước",
                convertDrawableToByteArray(ResourcesCompat.getDrawable(context.getResources(), R.drawable.phobo, null)),
                "Phở Bò là một món ăn quốc gia của Việt Nam, một món ăn mà nhiều người Việt yêu thích. Dưới đây là cách chế biến Phở Bò:\n" +
                        "\n" +
                        "\t\t+ Nguyên liệu: Phở Bò chủ yếu được làm từ xương cục (xương ống), thịt bò, hành khô, gừng, quế, hoa hồi, trái thảo (quả), muối, hạt nêm, nước mắm, chín (mì, bột ngọt), hành lá (rau mùi, ngò ri), bánh phở1.\n" +
                        "\t\t+ Hương vị: Phở Bò có hương vị thơm ngon từ nhân thịt bùi ngọt bên trong và lớp vỏ mềm mại bên ngoài1.\n" +
                        "\t\t+ Cách chế biến: Phở Bò được chế biến bằng cách nấu sôi các nguyên liệu như xương cục (xương ống), thịt bò trong nước dùng xương thơm vị đặc trưng của lá hẹ1.\n" +
                        "\t\t+ Phục vụ: Phở Bò thường được phục vụ nóng hổi cùng với các loại rau sống và gia vị1.\n" +
                        "\t\t+ Đánh giá: Món ăn này được đánh giá rất cao bởi người dùng", 6));

        // region Tra sua
        foodList.add(new Food(1, "Trà sữa dâu", "Trà sữa",
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
        foodList.add(new Food(1, "Trà sữa matcha", "Trà sữa",
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
        foodList.add(new Food(1, "Trà sữa truyền thống", "Trà sữa",
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
        foodList.add(new Food(1, "Trà sữa xoài", "Trà sữa",
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

        // region foodSize
        foodSizeList = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 55; i++) {
            foodSizeList.add(new FoodSize(i, 1, (random.nextInt(20) + 1) * 1000d));
            foodSizeList.add(new FoodSize(i, 2, (random.nextInt(20) + 21) * 1000d));
            foodSizeList.add(new FoodSize(i, 3, (random.nextInt(20) + 41) * 1000d));
        }

        // region foodSaved
        foodSavedList = new ArrayList<>();
        foodSavedList.add(new FoodSaved(1, 3, 1));
        foodSavedList.add(new FoodSaved(36, 3, 2));
        foodSavedList.add(new FoodSaved(3, 3, 2));
        foodSavedList.add(new FoodSaved(42, 3, 2));
        foodSavedList.add(new FoodSaved(11, 3, 1));
        foodSavedList.add(new FoodSaved(28, 1, 4));
        foodSavedList.add(new FoodSaved(40, 3, 3));
        foodSavedList.add(new FoodSaved(3, 3, 3));
        foodSavedList.add(new FoodSaved(42, 3, 3));
        foodSavedList.add(new FoodSaved(31, 3, 3));
        foodSavedList.add(new FoodSaved(20, 1, 4));

        // region notify
        notifyList = new ArrayList<>();
        notifyList.add(new Notify(1, "Chào bạn mới!",
                "Chào mừng bạn đến với ứng dụng Ứng dụng Đặt Đồ Ăn!", "02/09/2023"));
        notifyList.add(new Notify(2, "Thông báo chung!",
                "Ứng dụng Đặt Đồ Ăn dùng cho việc lựa chọn thưởng thức món ăn tại nhánh chóng.", "02/09/2023"));
        notifyList.add(new Notify(3, "Bạn đi đâu đấy!",
                "Ứng dụng Đặt Đồ Ăn luôn luôn chào đón bạn trải nghiệm.", "02/09/2023"));
        notifyList.add(new Notify(4, "Người quản lý app!",
                "Hoàng Anh Tiến(2k3) - Vũ Mạnh Chiến(2k3) - Nguyễn Chí Hải Anh(2k3).", "02/09/2023"));

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

        // Add restaurant
        for (Restaurant restaurant : restaurantList) {
            String sql = "INSERT INTO tblRestaurant VALUES(null, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, restaurant.getName());
            statement.bindString(2, restaurant.getAddress());
            statement.bindString(3, restaurant.getPhone());
            statement.bindBlob(4, restaurant.getImage());
            statement.executeInsert();
        }

        // Add restaurant saved
        for (RestaurantSaved restaurantSaved : restaurantSavedList) {
            db.execSQL("INSERT INTO tblRestaurantSaved VALUES(" + restaurantSaved.getRestaurantId() + ", " + restaurantSaved.getUserId() + ")");
        }

        // Add food
        for (Food food : foodList) {
            String sql = "INSERT INTO tblFood VALUES (null, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, food.getName());
            statement.bindString(2, food.getType());
            statement.bindBlob(3, food.getImage());
            statement.bindString(4, food.getDescription());
            statement.bindLong(5, food.getRestaurantId());
            statement.executeInsert();
        }

        // Add food size
        for (FoodSize foodSize : foodSizeList) {
            String sql = "INSERT INTO tblFoodSize VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, foodSize.getFoodId());
            statement.bindLong(2, foodSize.getSize());
            statement.bindDouble(3, foodSize.getPrice());
            statement.executeInsert();
        }

        // Add food saved
        for (FoodSaved foodSaved : foodSavedList) {
            String sql = "INSERT INTO tblFoodSaved VALUES(?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindLong(1, foodSaved.getFoodId());
            statement.bindLong(2, foodSaved.getSize());
            statement.bindLong(3, foodSaved.getUserId());
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
                    orderDetail.getFoodId() + ", " +
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

        //Create table "Restaurant" => để lưu ảnh trong SQLite ta dùng BLOG (Binary Longer Object)
        String queryCreateRestaurant = "CREATE TABLE IF NOT EXISTS tblRestaurant(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200), " +
                "address NVARCHAR(200)," +
                "phone CHAR(10)," +
                "image BLOB)";
        sqLiteDatabase.execSQL(queryCreateRestaurant);

        //Create table "RestaurantSaved"
        String queryCreateRestaurantSaved = "CREATE TABLE IF NOT EXISTS tblRestaurantSaved(" +
                "restaurant_id INTEGER, user_id INTEGER," +
                "PRIMARY KEY(restaurant_id, user_id))";
        sqLiteDatabase.execSQL(queryCreateRestaurantSaved);

        //Create table "Food"
        String queryCreateFood = "CREATE TABLE IF NOT EXISTS tblFood(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name NVARCHAR(200)," +
                "type NVARCHAR(200)," +
                "image BLOB," +
                "description NVARCHAR(200)," +
                "restaurant_id INTEGER)";
        sqLiteDatabase.execSQL(queryCreateFood);

        //Create table "FoodSize"
        String queryCreateFoodSize = "CREATE TABLE IF NOT EXISTS tblFoodSize(" +
                "food_id INTEGER," +
                "size INTEGER ," +
                "price DOUBLE," +
                "PRIMARY KEY (food_id, size))";
        sqLiteDatabase.execSQL(queryCreateFoodSize);

        //Create table "FoodSaved"
        String queryCreateFoodSaved = "CREATE TABLE IF NOT EXISTS tblFoodSaved(" +
                "food_id INTEGER," +
                "size INTEGER ," +
                "user_id INTEGER," +
                "PRIMARY KEY (food_id, size, user_id))";
        sqLiteDatabase.execSQL(queryCreateFoodSaved);

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
                "food_id INTEGER," +
                "size INTEGER," +
                "price DOUBLE," +
                "quantity INTEGER," +
                "PRIMARY KEY (order_id, food_id, size))";
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFoodSize");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblFood");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrderDetail");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblOrder");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblRestaurantSaved");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblRestaurant");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblUser");

        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
