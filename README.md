
# SQLite数据类型

一般数据采用的固定的静态数据类型，而SQLite采用的是动态数据类型，会根据存入值自动判断。SQLite具有以下五种常用的数据类型：

1. NULL: 这个值为空值;
2. VARCHAR(n)：长度不固定且其最大长度为 n 的字串，n不能超过 4000;
3. CHAR(n)：长度固定为n的字串，n不能超过 254;
4. INTEGER: 值被标识为整数,依据值的大小可以依次被存储为1,2,3,4,5,6,7,8;
5. REAL: 所有值都是浮动的数值,被存储为8字节的IEEE浮动标记序号;
6. TEXT: 值为文本字符串,使用数据库编码存储(TUTF-8, UTF-16BE or UTF-16-LE);
7. BLOB: 值是BLOB数据块，以输入的数据格式进行存储。如何输入就如何存储,不改  变格式;
8. DATA ：包含了 年份、月份、日期;
9. TIME： 包含了 小时、分钟、秒;

## SQLite常用的方法

>创建数据库
>
openOrCreateDatabase(Stringpath,SQLiteDatabase.CursorFactory  factory)

>插入数据
>
>insert(String table,String nullColumnHack,ContentValues  values)

>删除数据
>
>delete(String table,String whereClause,String[]  whereArgs)
>
>更新数据
>
>update(String table,ContentValues values,String whereClause,String[]  whereArgs) 

### insert插入数据
>insert(String table,String nullColumnHack,ContentValues  values)
传入的参数:1,表名,2,空列的默认值,3,ContentValues,类似于hashMap,是一种储存的机制,用法为:

	/**
     * 创建一个用来插入数据的方法
     *
     * @param name  姓名
     * @param sex   性别
     * @param age   年龄
     * @param hobby 爱好
     */
    public void insert(String name, String sex, String age, String hobby) {
        //让数据库可写
        SQLiteDatabase database = getWritableDatabase();
        /*
        类似于HashMap 都有键值对
        key 对应的列表中的某一列的名称,字段
        value 对应字段要插入的值
         */
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("age", age);
        values.put("hobby", hobby);
        //插入
        database.insert(TABLE_NAME, null, values);
        //插入完成后关闭,以免造成内存泄漏
        database.close();

    }

### delete删除数据
>delete(String table,String whereClause,String[]  whereArgs)
传入的参数:
>
1. 参数1, 表名称   
2. 参数2 , 删除条件
3. 参数3 , 删除条件值数组,用法为:

	/**
     * 创建一个删除数据的方法,传入的参数越多,删除时越精确的找到要删除的哪一行
     *
     * @param name
     * @param sex
     * @param age
     * @param hobby
     */
    public void delete(int id, String name, String sex, String age, String hobby) {
        SQLiteDatabase database = getWritableDatabase();
        /*
        删除的条件,当id = 传入的参数id时,sex = 传入的参数sex时,age = 传入的age,hobby = 传入的hobby时
        当条件都满足时才删除这行数据,一个条件不满足就删除失败
         */
        String where = "id=? and name = ? and sex = ? and age = ? and hobby = ?";
        //删除条件的参数
        String[] whereArgs = {id + "", name, sex, age, hobby};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

    /**
     * 再创建一个删除一个删除的方法,条件只有一个
     */
    public void delete(int id) {
        SQLiteDatabase database = getWritableDatabase();
        //当条件满足id = 传入的参数的时候,就删除那整行数据,有可能有好几行都满足这个条件,满足的全部都删除
        String where = "id = ?";
        String[] whereArgs = {id + ""};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

### updata修改数据
>update(String table,ContentValues values,String  whereClause, String[]  whereArgs)方法
传入的参数:
>
1. 参数1  表名称
2. 参数2  跟行列ContentValues类型的键值对Key-Value
3. 参数3  更新条件（where字句）
4. 参数4  更新条件数组

	/**
     * 创建一个修改数据的方法
     * @param id   条件,修改id为"id"的那一行数据
     * @param name
     * @param sex
     * @param age
     * @param hobby
     */
    public void updata(int id,String name, String sex, String age, String hobby) {
        SQLiteDatabase database = getWritableDatabase();
	//        update(String table,ContentValues values,String  whereClause, String[]  whereArgs)
        String where = "id = ?";
        String[] whereArgs = {id+""};
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("age", age);
        values.put("hobby", hobby);
        //参数1  表名称	参数2  跟行列ContentValues类型的键值对Key-Value
       // 参数3  更新条件（where字句）	参数4  更新条件数组
        database.update(TABLE_NAME, values,where, whereArgs);
        database.close();

### query查找数据
>query(String table,String[] columns,String selection,String[]  selectionArgs,String groupBy,String having,String orderBy,String limit)
传入参数:
>
1. 参数table:表名称
2. 参数columns:列名称数组
3. 参数selection:条件字句，相当于where
4. 参数selectionArgs:条件字句，参数数组
5. 参数groupBy:分组列
6. 参数having:分组条件
7. 参数orderBy:排序列
8. 参数limit:分页查询限制
	
	  /**
     * 创建一个查找数据库的方法
     *
     * public  Cursor query(String table,String[] columns,String selection,String[]  selectionArgs,String groupBy,String having,String orderBy,String limit);
     各个参数的意义说明：
     参数table:表名称
     参数columns:列名称数组
     参数selection:条件字句，相当于where
     参数selectionArgs:条件字句，参数数组
     参数groupBy:分组列
     参数having:分组条件
     参数orderBy:排序列
     参数limit:分页查询限制
     参数Cursor:返回值，相当于结果集ResultSet
     Cursor是一个游标接口，提供了遍历查询结果的方法，如移动指针方法move()，获得列值方法getString()等.
     */
    public Cursor query() {
        //数据库可读
        SQLiteDatabase database = getReadableDatabase();
        //查找
        Cursor query = database.query(TABLE_NAME, null, null, null, null, null, null);
        return query;
    }

>循环遍历,拿到数据

	 /**
     * 通过查找数据库,拿到里面的数据
     */
    private List<Map<String, Object>> getData() {
        list = new ArrayList<>();
        Cursor query = MyDataBase.getInstances(MainActivity.this).query();
        /*
        游标cursor默认是在-1的位置,query.moveToFirst()将游标移动到第一行,如果不写这个就会报
         Caused by: android.database.CursorIndexOutOfBoundsException: Index -1 requested, with a size of 12
         这个问题坑爹,以后一定要注意
         */
        if (query.moveToFirst()) {
            do {
                String name = query.getString(query.getColumnIndex("name"));
                String sex = query.getString(query.getColumnIndex("sex"));
                String age = query.getString(query.getColumnIndex("age"));
                String hobby = query.getString(query.getColumnIndex("hobby"));
                int id = query.getInt(query.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                map.put("sex", sex);
                map.put("age", age);
                map.put("hobby", hobby);
                list.add(map);
            } while (query.moveToNext());
        }
        //关闭查询游标
        query.close();
        return list;
    }

### 操作数据库的类

	package com.duanlian.databasedemo;

	import android.content.ContentValues;
	import android.content.Context;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;

	public class MyDataBase extends SQLiteOpenHelper {
    //数据库名字
    public static final String DB_NAME = "Students.db";
    //数据库版本
    public static final int DB_VERSION = 1;
    //表名
    public static final String TABLE_NAME = "student";
    public static MyDataBase myDataBase;

    /**
     * 单例模式返回数据库
     *
     * @param context 上下文
     * @return 数据库对象
     */
    public static MyDataBase getInstances(Context context) {
        if (myDataBase == null) {
            return new MyDataBase(context);
        } else {
            return myDataBase;
        }
    }


    //上下文,数据库名字,数据库工厂,版本号
    public MyDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //此方法中创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //这个有个坑,create table"+" " + TABLE_NAME 中间一定要加空格,别问为什么,我也不知道,不加就语法错误,吐血
        sqLiteDatabase.execSQL("create table" + " " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,name text,sex text,age text,hobby text);");

    }

    /**
     * 用来更新数据库版本的
     * onCreate方法只是在第一次安装app的时候会调用,之后的数据库要更改的话,必须使用数据库版本上升,或者卸载了重新安装
     *
     * @param sqLiteDatabase 数据库
     * @param oldVersion     老的版本号
     * @param newVersion     更新后的版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            //删除老表
            sqLiteDatabase.execSQL("drop" + TABLE_NAME);
            //重新创建表
            onCreate(sqLiteDatabase);
        }

    }

    /**
     * 创建一个用来插入数据的方法
     *
     * @param name  姓名
     * @param sex   性别
     * @param age   年龄
     * @param hobby 爱好
     */
    public void insert(String name, String sex, String age, String hobby) {
        //让数据库可写
        SQLiteDatabase database = getWritableDatabase();
        /*
        类似于HashMap 都有键值对
        key 对应的列表中的某一列的名称,字段
        value 对应字段要插入的值
         */
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("age", age);
        values.put("hobby", hobby);
        //插入
        database.insert(TABLE_NAME, null, values);
        //插入完成后关闭,以免造成内存泄漏
        database.close();

    }


    /**
     * 创建一个查找数据库的方法
     *
     * public  Cursor query(String table,String[] columns,String selection,String[]  selectionArgs,String groupBy,String having,String orderBy,String limit);
     各个参数的意义说明：
     参数table:表名称
     参数columns:列名称数组
     参数selection:条件字句，相当于where
     参数selectionArgs:条件字句，参数数组
     参数groupBy:分组列
     参数having:分组条件
     参数orderBy:排序列
     参数limit:分页查询限制
     参数Cursor:返回值，相当于结果集ResultSet
     Cursor是一个游标接口，提供了遍历查询结果的方法，如移动指针方法move()，获得列值方法getString()等.
     */
    public Cursor query() {
        //数据库可读
        SQLiteDatabase database = getReadableDatabase();
        //查找
        Cursor query = database.query(TABLE_NAME, null, null, null, null, null, null);
        return query;
    }

    /**
     * 创建一个删除数据的方法,传入的参数越多,删除时越精确的找到要删除的哪一行
     *
     * @param name
     * @param sex
     * @param age
     * @param hobby
     */
    public void delete(int id, String name, String sex, String age, String hobby) {
        SQLiteDatabase database = getWritableDatabase();
        /*
        删除的条件,当id = 传入的参数id时,sex = 传入的参数sex时,age = 传入的age,hobby = 传入的hobby时
        当条件都满足时才删除这行数据,一个条件不满足就删除失败
         */
        String where = "id=? and name = ? and sex = ? and age = ? and hobby = ?";
        //删除条件的参数
        String[] whereArgs = {id + "", name, sex, age, hobby};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

    /**
     * 再创建一个删除一个删除的方法,条件只有一个
     */
    public void delete(int id) {
        SQLiteDatabase database = getWritableDatabase();
        //当条件满足id = 传入的参数的时候,就删除那整行数据,有可能有好几行都满足这个条件,满足的全部都删除
        String where = "id = ?";
        String[] whereArgs = {id + ""};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }

    /**
     * 创建一个修改数据的方法
     * @param id   条件,修改id为"id"的那一行数据
     * @param name
     * @param sex
     * @param age
     * @param hobby
     */
    public void updata(int id,String name, String sex, String age, String hobby) {
        SQLiteDatabase database = getWritableDatabase();
	//        update(String table,ContentValues values,String  whereClause, String[]  whereArgs)
        String where = "id = ?";
        String[] whereArgs = {id+""};
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("sex", sex);
        values.put("age", age);
        values.put("hobby", hobby);
        //参数1  表名称	参数2  跟行列ContentValues类型的键值对Key-Value
       // 参数3  更新条件（where字句）	参数4  更新条件数组
        database.update(TABLE_NAME, values,where, whereArgs);
        database.close();
  	  	}
	}


