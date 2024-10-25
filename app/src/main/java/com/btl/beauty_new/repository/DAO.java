package com.btl.beauty_new.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.*;

import java.util.ArrayList;
import java.util.Calendar;


public class DAO {
    DatabaseHandler dbHelper;
    SQLiteDatabase db;

    public DAO(Context context) {
        dbHelper = new DatabaseHandler(context);
        db = dbHelper.getReadableDatabase();
    }

    // region store
    public Store getStoreInformation(Integer storeId) {
        String query = "SELECT * FROM tblStore WHERE id=" + storeId;
        Cursor cursor = dbHelper.getDataRow(query);
        return new Store(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getBlob(4));
    }

    public Store getStoreByName(String storeName) {
        String query = "SELECT * FROM tblStore WHERE name='" + storeName + "'";
        Cursor cursor = dbHelper.getDataRow(query);
        return new Store(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getBlob(4));
    }

    public ArrayList<Store> getStoreList() {
        ArrayList<Store> storeArrayList = new ArrayList<>();
        String query = "SELECT * FROM tblStore";
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            storeArrayList.add(new Store(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getBlob(4)));
        }
        return storeArrayList;
    }

    // region storeSaved
    public boolean addStoreSaved(StoreSaved storeSaved) {
        String query = "INSERT INTO tblStoreSaved VALUES(" + storeSaved.getStoreId() + ", " + storeSaved.getUserId() + ")";
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean deleteStoreSaved(StoreSaved storeSaved) {   // del store
        String query = "DELETE FROM tblStoreSaved WHERE store_id=" + storeSaved.getStoreId() + " AND user_id=" + storeSaved.getUserId();
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public ArrayList<StoreSaved> getStoreSavedList(Integer userId) {    // get data store saved
        ArrayList<StoreSaved> storeSavedArrayList = new ArrayList<>();
        String query = "SELECT * FROM tblStoreSaved WHERE user_id=" + userId;
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            storeSavedArrayList.add(new StoreSaved(cursor.getInt(0), cursor.getInt(1)));
        }
        return storeSavedArrayList;
    }
    // endregion

    // region Order
    public Integer quantityOfOrder() {
        String query = "SELECT COUNT(*) FROM tblOrder WHERE status='Delivered'";
        Cursor cursor = dbHelper.getDataRow(query);
        return cursor.getInt(0);
    }

    public void addOrder(Order order) {
        String query = "INSERT INTO tblOrder VALUES(null," +
                order.getUserId() + ",'" +
                order.getAddress() + "','" +
                order.getDateOfOrder() + "'," +
                order.getTotalValue() + ",'" +
                order.getStatus() + "')";
        dbHelper.queryData(query);
    }

    public void updateOrder(Order order) {
        String query = "UPDATE tblOrder SET address='" + order.getAddress() +
                "', date_order='" + order.getDateOfOrder() +
                "', total_value=" + order.getTotalValue() +
                ", status='" + order.getStatus() +
                "' WHERE id=" + order.getId() +
                " AND user_id=" + order.getUserId();
        dbHelper.queryData(query);
    }

    public ArrayList<Order> getOrderOfUser(Integer userId, String status) {
        ArrayList<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT * FROM tblOrder WHERE user_id=" + userId + ") WHERE status='" + status + "'";
        if (status.equals("Delivered")) {
            query += " OR status='Canceled'";
        }
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            orderList.add(new Order(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getDouble(4),
                    cursor.getString(5)));
        }
        return orderList;
    }
    // endregion

    // region OrderDetail
    public OrderDetail getExistOrderDetail(Integer orderId, CosmeticSize cosmeticSize) {
        String query = "SELECT * FROM tblOrderDetail WHERE order_id=" + orderId +
                " AND cosmetic_id=" + cosmeticSize.getCosmeticId() +
                " AND size=" + cosmeticSize.getSize();
        Cursor cursor = dbHelper.getDataRow(query);
        if (cursor.moveToFirst()) {
            OrderDetail orderDetail = new OrderDetail(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getDouble(3), cursor.getInt(4));
            System.out.println(orderDetail);
            return orderDetail;
        }
        return null;
    }

    public boolean addOrderDetail(OrderDetail od) {
        String query = "INSERT INTO tblOrderDetail VALUES(" +
                od.getOrderId() + ", " +
                od.getCosmeticId() + ", " +
                od.getSize() + ", " +
                od.getPrice() + ", " +
                od.getQuantity() + ")";
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public boolean deleteOrderDetailByOrderIdAndCosmeticId(Integer orderId, Integer cosmeticId) {
        String query = "DELETE FROM tblOrderDetail WHERE cosmetic_id=" + cosmeticId + " and order_id=" + orderId;
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception err) {
            System.out.println(err); // in ra lỗi
            return false;
        }
    }

    public Cursor getCart(Integer userId) {
        return dbHelper.getDataRow("SELECT id FROM tblOrder WHERE status='Craft' AND user_id=" + userId);
    }

    public ArrayList<OrderDetail> getCartDetailList(Integer orderId) {
        ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
        String query = "SELECT * FROM tblOrderDetail WHERE order_id=" + orderId;
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            orderDetailArrayList.add(new OrderDetail(cursor.getInt(0), cursor.getInt(1),
                    cursor.getInt(2), cursor.getDouble(3), cursor.getInt(4)));
        }
        return orderDetailArrayList;
    }

    public boolean updateQuantity(OrderDetail orderDetail) {
        String query = "UPDATE tblOrderDetail SET quantity=" + orderDetail.getQuantity() +
                " WHERE order_id=" + orderDetail.getOrderId() +
                " AND cosmetic_id=" + orderDetail.getCosmeticId() +
                " AND size=" + orderDetail.getSize();
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    // endregion

    // region Notify
    public void addNotify(Notify n) {
        String query = "INSERT INTO tblNotify VALUES(null,'" +
                n.getTitle() + "', '" +
                n.getContent() + "', '" +
                n.getDateMake() + "')";
        dbHelper.queryData(query);
    }

    public void addNotifyToUser(NotifyToUser notifyToUser) {
        String query = "INSERT INTO tblNotifyToUser VALUES(" +
                notifyToUser.getNotifyId() + "," +
                notifyToUser.getUserId() + ")";
        dbHelper.queryData(query);
    }

    public Integer getNewestNotifyId() {
        String query = "SELECT * FROM tblNotify";
        Cursor cursor = dbHelper.getData(query);
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    public ArrayList<Notify> getSystemNotify() {
        ArrayList<Notify> notifyArrayList = new ArrayList<>();
        String query = "SELECT * FROM tblNotify WHERE id NOT IN (SELECT notify_id FROM tblNotifyToUser)";
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            notifyArrayList.add(new Notify(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        return notifyArrayList;
    }

    public ArrayList<Notify> getUserNotify(Integer userId) {
        ArrayList<Notify> notifyArrayList = new ArrayList<>();
        String query = "SELECT tblNotify.* FROM tblNotify, tblNotifyToUser " +
                "WHERE tblNotify.id = tblNotifyToUser.notify_id AND tblNotifyToUser.user_id=" + userId;
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            notifyArrayList.add(new Notify(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        return notifyArrayList;
    }
    // endregion

    // region User
    public void addUser(User user) {
        String query = "INSERT INTO tblUser VALUES(null,'" +
                user.getName() + "', '" +
                user.getGender() + "', '" +
                user.getDateOfBirth() + "', '" +
                user.getPhone() + "', '" +
                user.getUsername() + "', '" +
                user.getPassword() + "')";
        dbHelper.queryData(query);
    }

    public void updateUser(User user) {
        String query = "UPDATE tblUser SET " +
                "name='" + user.getName() + "'," +
                "gender='" + user.getGender() + "'," +
                "date_of_birth='" + user.getDateOfBirth() + "'," +
                "phone='" + user.getPhone() + "'," +
                "password='" + user.getPassword() + "' " +
                "WHERE id=" + user.getId();
        dbHelper.queryData(query);
    }

    public Integer getNewestUserId() {
        String query = "SELECT * FROM tblUser";
        Cursor cursor = dbHelper.getData(query);
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    public boolean UserExited(String username) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "'";
        Cursor cursor = dbHelper.getData(query);
        return cursor.moveToNext();
    }

    // lấy ra thông tin đăng nhập xem có tồn tại không
    public User getUserByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "' and password='" + password + "'";
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor.getCount() > 0) {
            return new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6));
        }
        return null;
    }


    // kiểm tra xem user đã tồn tại trong db hay không
    public boolean checkUsername(String username) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "'";
        Cursor cursor = dbHelper.getData(query);
        return cursor.getCount() > 0;
    }

    // kiểm tra xem password đã tồn tại trong db hay không
    public boolean checkPasswordToCurrentUsername(String username, String password) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "' and password='" + password + "'";
        Cursor cursor = dbHelper.getDataRow(query);
        return cursor.getCount() > 0;
    }


    public boolean signIn(User user) {
        User existedUser = getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        return existedUser != null;
    }
    // endregion

    // region cosmetic
    public CosmeticSize getCosmeticDefaultSize(Integer cosmeticId) {
        String sql = "SELECT * FROM tblCosmeticSize WHERE cosmetic_id=" + cosmeticId;
        Cursor cursor = dbHelper.getDataRow(sql);
        if (cursor == null)
            return null;
        return new CosmeticSize(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2));
    }

    public CosmeticSize getCosmeticSize(Integer cosmeticId, Integer size) {
        String sql = "SELECT * FROM tblCosmeticSize WHERE cosmetic_id=" + cosmeticId + " AND size=" + size;
        Cursor cursor = dbHelper.getDataRow(sql);
        if (cursor == null)
            return null;
        return new CosmeticSize(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2));
    }

    public ArrayList<CosmeticSize> getAllCosmeticSize(Integer cosmeticId) {
        ArrayList<CosmeticSize> cosmeticSizeList = new ArrayList<>();
        String sql = "SELECT * FROM tblCosmeticSize WHERE cosmetic_id=" + cosmeticId;
        Cursor cursor = dbHelper.getData(sql);
        while (cursor.moveToNext()) {
            cosmeticSizeList.add(new CosmeticSize(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2)));
        }
        return cosmeticSizeList;
    }

    public Cosmetic getCosmeticById(Integer id) {
        String query = "SELECT * FROM tblCosmetic WHERE id=" + id;
        Cursor cursor = dbHelper.getDataRow(query);
        return new Cosmetic(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3), cursor.getString(4), cursor.getInt(5));
    }

    public ArrayList<Cosmetic> getCosmeticByKeyWord(String keyword, Integer storeId) {
        ArrayList<Cosmetic> listCosmetic = new ArrayList<>();
        String query = "SELECT * FROM tblCosmetic WHERE name LIKE '%" + keyword + "%'";
        if (storeId != null) {
            query += " AND store_id=" + storeId;
        }

        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            listCosmetic.add(new Cosmetic(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5))
            );
        }
        return listCosmetic;
    }

    public ArrayList<Cosmetic> getCosmeticByType(String type) {
        ArrayList<Cosmetic> listCosmetic = new ArrayList<>();
        String query = "SELECT * FROM tblCosmetic WHERE type='" + type + "'";
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            listCosmetic.add(new Cosmetic(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5))
            );
        }
        return listCosmetic;
    }

    public ArrayList<Cosmetic> getCosmeticBystore(Integer storeId) {
        ArrayList<Cosmetic> listCosmetic = new ArrayList<>();
        String query = "SELECT * FROM tblCosmetic WHERE store_id=" + storeId;
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            listCosmetic.add(new Cosmetic(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getInt(5))
            );
        }
        return listCosmetic;
    }
    // endregion

    // region cosmetic Saved
    public ArrayList<CosmeticSaved> getCosmeticSaveList(Integer userId) {
        ArrayList<CosmeticSaved> cosmeticSavedArrayList = new ArrayList<>();
        String query = "SELECT * FROM tblCosmeticSaved WHERE user_id=" + userId;
        Cursor cursor = dbHelper.getData(query);
        while (cursor.moveToNext()) {
            cosmeticSavedArrayList.add(new CosmeticSaved(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2)));
        }
        return cosmeticSavedArrayList;
    }

    public boolean addCosmeticSaved(CosmeticSaved cosmeticSaved) {
        String query = "INSERT INTO tblCosmeticSaved VALUES(" + cosmeticSaved.getCosmeticId() + ", "
                + cosmeticSaved.getSize() + ", "
                + cosmeticSaved.getUserId() + ")";
        try {
            dbHelper.queryData(query);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

    public void deleteCosmeticSavedByCosmeticIdAndSize(Integer cosmeticId, Integer size) {
        String query = "DELETE FROM tblCosmeticSaved WHERE cosmetic_id=" +
                cosmeticId + " and size=" + size;
        dbHelper.queryData(query);
    }
    // endregion

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + month + "/" + year;
    }
}
