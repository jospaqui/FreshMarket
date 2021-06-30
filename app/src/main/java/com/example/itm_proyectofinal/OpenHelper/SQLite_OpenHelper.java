package com.example.itm_proyectofinal.OpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.itm_proyectofinal.BD.Estructura_BBDD;

import org.w3c.dom.Text;

import java.util.EmptyStackException;

public class SQLite_OpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "freshmarket.dbd";

    public SQLite_OpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Estructura_BBDD.SQL_CREATE_USER_CLASICO);
        db.execSQL(Estructura_BBDD.SQL_CREATE_USER_AGRICULTOR);
        db.execSQL(Estructura_BBDD.SQL_CREATE_PRODUCTO);
        db.execSQL(Estructura_BBDD.SQL_CREATE_REGISTRO);
        db.execSQL(Estructura_BBDD.SQL_CREATE_PRUEBA_PEDIDO);
        db.execSQL(Estructura_BBDD.SQL_CREATE_VENTA);
        db.execSQL(Estructura_BBDD.SQL_CREATE_VENTA_DETALLE);
        db.execSQL(Estructura_BBDD.SQL_CREATE_COMENTARIO);
        //db.execSQL();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Estructura_BBDD.SQL_DELETE_USER_CLASICO);
        db.execSQL(Estructura_BBDD.SQL_DELETE_USER_AGRICULTOR);
        db.execSQL(Estructura_BBDD.SQL_DELETE_USER_PRODUCTO);
        db.execSQL(Estructura_BBDD.SQL_DELETE_REGISTRO);
        db.execSQL(Estructura_BBDD.SQL_DELETE_PRUEBA_PEDIDO);
        db.execSQL(Estructura_BBDD.SQL_DELETE_VENTA);
        db.execSQL(Estructura_BBDD.SQL_DELETE_VENTA_DETALLE);
        db.execSQL(Estructura_BBDD.SQL_DELETE_COMENTARIO);
        onCreate(db);
    }


    public Cursor consultarUsuarioxCorreo(String correo){
        SQLiteDatabase bd= this.getReadableDatabase();
        String[] projection={
                "nombre_cla",
                "apellido_cla",
                "DNI_cla",
                "fecha_naci_cla",
                "celular_cla",
                "coreo_cla",
                "password_cla",
                "fecha_registro_cla"

        };
        String selection="coreo_cla"+" like ?";
        String[] selectionArgs={correo};
        Cursor c= bd.query("USUARIO_CLASICO",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if(c.getCount()>0){
            return c;
        }else{
            return null;
        }

    }



    public Cursor consultarUsuariosClasico(String usu, String cla){
        SQLiteDatabase bd= this.getReadableDatabase();
        String[] projection={
                "id_us_cla",
                "nombre_cla",
                "apellido_cla",
                "DNI_cla",
                "fecha_naci_cla",
                "celular_cla",
                "coreo_cla",
                "password_cla",
                "fecha_registro_cla"
        };
        String selection="coreo_cla"+" like ? and "+"password_cla"+" like ?";
        String[] selectionArgs={usu,cla};
        Cursor c= bd.query("USUARIO_CLASICO",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return c;

    }
    public Cursor consultarUsuariosAgricultor(String usu, String cla){
        SQLiteDatabase bd= this.getReadableDatabase();
        String[] projection={
                "id_us_agri",
                "nombre_agri",
                "apellido_agri",
                "DNI_agri",
                "fecha_naci_agri",
                "celular_agri",
                "departamento_agri",
                "provincia_agri",
                "direccion_agri",
                "tipo_agri",
                "correo_agri",
                "password_agri",
                "fecha_registro_agri"
        };
        String selection="correo_agri"+" like ? and "+"password_agri"+" like ?";
        String[] selectionArgs={usu,cla};
        Cursor c= bd.query("USUARIO_AGRICULTOR",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return c;

    }


    public Cursor consultarAgricultorxCorreo(String correo){
        SQLiteDatabase bd= this.getReadableDatabase();
        String[] projection={
                "nombre_agri",
                "apellido_agri",
                "DNI_agri",
                "fecha_naci_agri",
                "celular_agri",
                "departamento_agri",
                "provincia_agri",
                "direccion_agri",
                "tipo_agri",
                "correo_agri",
                "password_agri",
                "fecha_registro_agri"

        };
        String selection="correo_agri"+" like ?";
        String[] selectionArgs={correo};
        Cursor c= bd.query("USUARIO_AGRICULTOR",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if(c.getCount()>0){
            return c;
        }else{
            return null;
        }

    }

    public Cursor consultarProductoxNombre(String nombre, int id_Agri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT R.id_us_agric,P.id_prod,P.nombre_prod,P.stock_prod,P.criterio_medida_pro FROM REGISTRO R INNER JOIN PRODUCTO P ON R.id_produc=P.id_prod WHERE (P.nombre_prod like '"+nombre+"') and (R.id_us_agric="+id_Agri+")",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
            if(cursor.getCount()>0){
                return cursor;
            }else{
                return null;
            }

    }

    public Cursor consultarProductoxNombreDiferente(String nombre, int id_Agri, int codpro){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT R.id_us_agric,P.id_prod,P.nombre_prod,P.stock_prod,P.criterio_medida_pro FROM REGISTRO R INNER JOIN PRODUCTO P ON R.id_produc=P.id_prod WHERE P.id_prod NOT IN("+codpro+") AND (P.nombre_prod like '"+nombre+"') and (R.id_us_agric="+id_Agri+")",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }

    public Cursor consultarProductoxidAgri(int id_Agri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT R.id_us_agric,P.id_prod,P.nombre_prod FROM REGISTRO R INNER JOIN PRODUCTO P ON R.id_produc=P.id_prod WHERE (R.id_us_agric="+id_Agri+")",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);

        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }

    /*public Cursor consultarProductoxidAgrixProd(int id_Agri,String nomb){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT R.id_us_agric,P.id_prod,P.nombre_prod FROM REGISTRO R INNER JOIN PRODUCTO P ON R.id_produc=P.id_prod WHERE (R.id_us_agric="+id_Agri+") AND P.nombre_prod",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);

        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }*/

    public Cursor consultarProductoxIDxNombre(String nombre,int id_Agri,int id_Prod){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT R.id_us_agric,P.id_prod FROM REGISTRO R INNER JOIN PRODUCTO P ON R.id_produc=P.id_prod WHERE R.id_produc="+id_Agri+" AND P.nombre_prod LIKE "+nombre+" AND P.id_produc="+id_Prod,null);
        Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if (cursor2.getCount()>0){
            if(cursor.getCount()>0){
                return cursor;
            }else{
                return null;
            }
        }else{
            return null;
        }


    }


    public Cursor obtenerIdProducto() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT MAX(id_prod) FROM PRODUCTO ORDER BY id_prod DESC", null);//DUDA SI EXISTE TOP(1) Y SI EL ORDENAMIENTO ESTA BIEN

        //Cursor cursor = bd.rawQuery("SELECT TOP(1) id_prod FROM REGISTRO R INNER JOIN PRODUCTO P ON R.id_produc=P.id_prod ORDER BY id_prod DESC", null);//DUDA SI EXISTE TOP(1) Y SI EL ORDENAMIENTO ESTA BIEN
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
        /*while (cursor.moveToNext()) {
            usu = new Usuario();
            usu.setId(cursor.getInt(0));
            usu.setNombre(cursor.getString(1));
            usu.setDistrito(cursor.getString(2));
            usu.setCorreo(cursor.getString(3));
            usu.setPassword(cursor.getString(4));
            lstUsuario.add(usu);
        }*/
    }

    public Cursor obtenerUsuarioAgri(int id) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIO_AGRICULTOR WHERE id_us_agri="+id, null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }



    public Cursor obtenerUsuarioAgriTotales() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIO_AGRICULTOR", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor obtenerUsuarioAgriTotalesAgrupado() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT nombre_agri,apellido_agri FROM USUARIO_AGRICULTOR GROUP BY nombre_agri,apellido_agri", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    public Cursor consultarAgricultorxCorreoModificado(int id,String correo) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIO_AGRICULTOR WHERE id_us_agri NOT IN("+id+") AND correo_agri LIKE '"+correo+"'", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }



    //USUARIO CLASICO

    public Cursor obtenerUsuarioCla(int id) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIO_CLASICO WHERE id_us_cla="+id, null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor consultarClasicoxCorreoModificado(int id,String correo) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIO_CLASICO WHERE id_us_cla NOT IN("+id+") AND coreo_cla LIKE '"+correo+"'", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    public Cursor obtenerUsuarioClaTotales() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIO_CLASICO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    public Cursor mostrarProductos() {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE P.stock_prod>0 ORDER BY R.fecha_reg DESC", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor mostrarProductosxid(int codPro) {
        SQLiteDatabase bd = this.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT P.id_prod,P.nombre_prod,P.precio_prod,P.criterio_medida_pro,P.stock_prod,P.foto_prod,P.descrip_prod,R.id_us_agric,U.nombre_agri,U.apellido_agri FROM PRODUCTO P INNER JOIN REGISTRO R ON P.id_prod=R.id_produc INNER JOIN USUARIO_AGRICULTOR U ON U.id_us_agri=R.id_us_agric WHERE R.id_produc="+codPro, null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }




    //REGISTRO

    public Cursor consultarRegistro(int id_prod){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT R.id_us_agric,U.nombre_agri,U.apellido_agri FROM REGISTRO R INNER JOIN USUARIO_AGRICULTOR U ON R.id_us_agric=U.id_us_agri WHERE (R.id_produc="+id_prod+")",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);

        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }





    //PRODUCTO

    public Cursor consultarProductos(int idprod){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM PRODUCTO WHERE id_prod="+idprod,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);

        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }

    public Cursor consultarProductosxCant(int idprod,int cant){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM PRODUCTO WHERE id_prod="+idprod+" AND stock_prod >= "+cant,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);

        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }


    //PRUEBA_PEDIDO
    public Cursor consultarPedidoPrueba(int idprod,int idCla){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM PEDIDO_PRUEBA WHERE id_producto_pe="+idprod+" AND id_clasico_pe="+idCla,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }


    public Cursor consultarMontoTotal(int idCla){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT SUM(precio_parcial_pe) FROM PEDIDO_PRUEBA WHERE id_clasico_pe="+idCla,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }

    public Cursor consultarPedidoxID(int idCla){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT id_agricul_pe,id_clasico_pe FROM PEDIDO_PRUEBA WHERE id_clasico_pe="+idCla+" GROUP BY id_agricul_pe,id_clasico_pe",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }

    public Cursor consultarPedidoxIDClaxAgri(int idCla,int idAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM PEDIDO_PRUEBA WHERE id_clasico_pe="+idCla+" AND id_agricul_pe="+idAgri,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }

    }

    //VENTA

    public Cursor maxIdVenta(){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT MAX(id_venta) FROM VENTA",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    public Cursor consultarVentaxAgri(int Agri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM VENTA WHERE id_agricultor="+Agri+" ORDER BY fecha_venta DESC",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    //VENTA_DETALLE
    public Cursor consultaVentaDetalle(int codVenta){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM VENTA_DETALLE WHERE id_v="+codVenta,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor consultaVentaDetallexProducto(int codVenta){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT P.nombre_prod,P.precio_prod,P.criterio_medida_pro,VD.cantidad,VD.precio_cant_prodc FROM VENTA_DETALLE VD INNER JOIN PRODUCTO P ON P.id_prod=VD.id_prodc WHERE VD.id_v="+codVenta,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    //usuario_clasico
    public Cursor user_clas_datos(int cod){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM USUARIO_CLASICO WHERE id_us_cla="+cod,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    //USER_AGRICULTOR
    public Cursor user_agri_datos(int cod){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT * FROM USUARIO_AGRICULTOR WHERE id_us_agri="+cod,null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    //ESTADISTICAS

    public Cursor cantidadxProductoVendido(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT VD.id_prodc,P.nombre_prod,P.criterio_medida_pro,SUM(VD.cantidad) FROM VENTA V INNER JOIN VENTA_DETALLE VD ON V.id_venta=VD.id_v INNER JOIN PRODUCTO P ON P.id_prod=VD.id_prodc WHERE V.id_agricultor="+codAgri+" GROUP BY VD.id_prodc,P.nombre_prod,P.criterio_medida_pro",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor cantidadVentas(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT COUNT(*) FROM VENTA  WHERE id_agricultor="+codAgri+" GROUP BY id_agricultor",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor cantidadProductos(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT COUNT(*) FROM REGISTRO  WHERE id_us_agric="+codAgri+" GROUP BY id_us_agric",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }


    public Cursor ingresoTotal(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT SUM(monto_total) FROM VENTA  WHERE id_agricultor="+codAgri+" GROUP BY id_agricultor",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor productoDestacado(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT P.nombre_prod,SUM(VD.precio_cant_prodc) FROM VENTA V INNER JOIN VENTA_DETALLE VD ON V.id_venta=VD.id_v INNER JOIN PRODUCTO P ON P.id_prod=VD.id_prodc WHERE V.id_agricultor="+codAgri+" GROUP BY P.nombre_prod ORDER BY SUM(VD.precio_cant_prodc) DESC",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor clienteFrecuente(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT UC.nombre_cla,UC.apellido_cla,COUNT(V.id_clasico) FROM VENTA V INNER JOIN USUARIO_CLASICO UC ON UC.id_us_cla=V.id_clasico WHERE V.id_agricultor="+codAgri+" GROUP BY UC.nombre_cla,UC.apellido_cla ORDER BY COUNT(V.id_clasico) DESC , V.fecha_venta DESC",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }

    public Cursor clienteDestacado(int codAgri){
        SQLiteDatabase bd= this.getReadableDatabase();
        Cursor cursor= bd.rawQuery("SELECT UC.nombre_cla,UC.apellido_cla,SUM(VD.precio_cant_prodc) FROM VENTA_DETALLE VD INNER JOIN  VENTA V ON VD.id_v=V.id_venta INNER JOIN USUARIO_CLASICO UC ON UC.id_us_cla=V.id_clasico WHERE V.id_agricultor="+codAgri+" GROUP BY UC.nombre_cla,UC.apellido_cla ORDER BY SUM(VD.precio_cant_prodc) DESC",null);
        //Cursor cursor2 = bd.rawQuery("SELECT * FROM PRODUCTO", null);
        if(cursor.getCount()>0){
            return cursor;
        }else{
            return null;
        }
    }







    /*public void consultaListarUsuarios(){
        SQLiteDatabase db= helper.getReadableDatabase();
        Usuario usu=null;
        Cursor cursor= db.rawQuery("SELECT * FROM "+ Estructura_BBDD.TABLE_NAME,null);
        while (cursor.moveToNext()){
            usu= new Usuario();
            usu.setId(cursor.getInt(0));
            usu.setNombre(cursor.getString(1));
            usu.setDistrito(cursor.getString(2));
            usu.setCorreo(cursor.getString(3));
            usu.setPassword(cursor.getString(4));
            lstUsuario.add(usu);
        }
    }*/


    /*public  void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }
    public void openBD(){
        this.getWritableDatabase();

    }*/
    /*public Cursor consultarUsuarios(String usu, String cla){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                Estructura_BBDD.NOMBRE_COLUMN2,
                Estructura_BBDD.NOMBRE_COLUMN3,
                Estructura_BBDD.NOMBRE_COLUMN4,
                Estructura_BBDD.NOMBRE_COLUMN5,
                Estructura_BBDD.NOMBRE_COLUMN6,
                Estructura_BBDD.NOMBRE_COLUMN7,
                Estructura_BBDD.NOMBRE_COLUMN8

        };
        String selection = Estructura_BBDD.NOMBRE_COLUMN6 + " like ? and " + Estructura_BBDD.NOMBRE_COLUMN8 + " like ?";
        String[] selectionArgs = {usu,cla};
        Cursor c = db.query(Estructura_BBDD.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
        return c;
    }*/
}
