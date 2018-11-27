package com.jlzolp.lorenzo.usbkotlin;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.Set;

public class Configuracion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    /*
     *  Las notificaciones de UsbService serán recibidas aquí.
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbServicio.ACTION_USB_PERMISSION_GRANTED: // USB PERMISO CONCEDIDO
                    Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
                    break;
                case UsbServicio.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISO NO CONCEDIDO
                    Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
                    break;
                case UsbServicio.ACTION_NO_USB: // USB NO  CONECTADO
                    Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbServicio.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
                    break;
                case UsbServicio.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private UsbServicio usbServicio;
    private CheckBox box9600, box38400;
    Spinner spBauds,spDatos,spControl,spParidad,spStop;
    private View Btn_Deflt;

    private View BtnR;
    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbServicio = ((UsbServicio.UsbBinder) arg1).getService();
            //usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbServicio = null;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);


        BtnsSpinner();
        Spinner spBauds = (Spinner) findViewById(R.id.SpBauds);
        spBauds.setOnItemSelectedListener(this);

        Spinner spDatos = (Spinner) findViewById(R.id.SpDatos);
        spDatos.setOnItemSelectedListener(this);


        Spinner spStop = (Spinner) findViewById(R.id.spStop);
        spStop.setOnItemSelectedListener(this);

        Spinner spParidad = (Spinner) findViewById(R.id.spParidad);
        spParidad.setOnItemSelectedListener(this);

        Spinner spControl = (Spinner) findViewById(R.id.spControl);
        spControl.setOnItemSelectedListener(this);


        Btn_Deflt = (Button) findViewById(R.id.Reset);
        Btn_Deflt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Configuracion.this,"Valores ",Toast.LENGTH_LONG ).show();
                Default_Parametros();


            }
        });




    }
    /*
        serialPort.setBaudRate(BAUD_RATE);
        serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
        serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
        serialPort.setParity(UsbSerialInterface.PARITY_NONE);

    */
    /////////////////////////////////////////////////////////////////
    //             Board select menu
    /////////////////////////////////////////////////////////////////
    private static final int MENU_ID_BOARD      = 1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        //menu.add(Menu.NONE, MENU_ID_BOARD, Menu.NONE, "Opciones");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.add4:
                // Toast.makeText(Configurac.this,"Ya estas aqui, Puñetas",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(Configuracion.this,MainActivity.class);
                startActivity(intent);
                //System.exit(0);
                return true;
            case R.id.add5:
                // Toast.makeText(Configurac.this,"Ya estas aqui, Puñetas",Toast.LENGTH_LONG ).show();
                finish();
                Intent inten = new Intent(Intent.ACTION_MAIN);
                inten.addCategory(Intent.CATEGORY_HOME);
                inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inten);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void BtnsSpinner(){
        Spinner spBauds = (Spinner) findViewById(R.id.SpBauds);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Baud, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBauds.setAdapter(adapter);

        //Toast.makeText(this,,Toast.LENGTH_LONG ).show();

        Spinner spDatos = (Spinner) findViewById(R.id.SpDatos);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Data, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDatos.setAdapter(adapter2);

        Spinner spStop = (Spinner) findViewById(R.id.spStop);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Stop, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStop.setAdapter(adapter3);

        Spinner spParidad = (Spinner) findViewById(R.id.spParidad);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.Parity, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spParidad.setAdapter(adapter4);

        Spinner spControl = (Spinner) findViewById(R.id.spControl);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,
                R.array.Control, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spControl.setAdapter(adapter5);


    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using

        switch (parent.getId()){
            case R.id.SpBauds:
                parent.getItemIdAtPosition(pos);
                //Toast.makeText(parent.getContext(), "Spbauds", Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "9600", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(9600);
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "600", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(600);
                        break;
                    case 2:
                        //Toast.makeText(parent.getContext(), "1200", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(1200);
                        break;
                    case 3:
                        // Toast.makeText(parent.getContext(), "2400", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(2400);
                        break;
                    case 4:
                        //Toast.makeText(parent.getContext(), "4800", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(4800);
                        break;
                    case 5:
                        //Toast.makeText(parent.getContext(), "9600", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(9600);
                        break;
                    case 6:
                        //Toast.makeText(parent.getContext(), "14400", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(14400);
                        break;
                    case 7:
                        //Toast.makeText(parent.getContext(), "19200", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(19200);
                        break;
                    case 8:
                        //Toast.makeText(parent.getContext(), "28800", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(28800);
                        break;
                    case 9:
                        //Toast.makeText(parent.getContext(), "38400", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(38400);
                        break;
                    case 10:
                        //Toast.makeText(parent.getContext(), "115200", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(115200);
                        break;
                    case 11:
                        //Toast.makeText(parent.getContext(), "230400", Toast.LENGTH_SHORT).show();
                        usbService.changeBaudRate(230400);
                        break;
                    default:
                        return;
                }
            case R.id.SpDatos:
                parent.getItemAtPosition(pos);
                // Toast.makeText(parent.getContext(), "Spdato", Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "5", Toast.LENGTH_SHORT).show();
                        usbService.changeBits(8);
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "6", Toast.LENGTH_SHORT).show();
                        usbService.changeBits(7);
                        break;
                    case 2:
                        // Toast.makeText(parent.getContext(), "7", Toast.LENGTH_SHORT).show();
                        usbService.changeBits(6);
                        break;
                    case 4:
                        //Toast.makeText(parent.getContext(), "8", Toast.LENGTH_SHORT).show();
                        usbService.changeBits(5);
                        break;
                    default:
                        return;
                }
            case R.id.spParidad:
                parent.getItemAtPosition(pos);
                // Toast.makeText(parent.getContext(), "Spdato", Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0:
                        // Toast.makeText(parent.getContext(), "Even", Toast.LENGTH_SHORT).show();
                        usbService.changeParidad(0);
                        break;
                    case 1:
                        // Toast.makeText(parent.getContext(), "Odd", Toast.LENGTH_SHORT).show();
                        usbService.changeParidad(1);
                        break;
                    case 2:
                        //Toast.makeText(parent.getContext(), "None", Toast.LENGTH_SHORT).show();
                        usbService.changeParidad(2);
                        break;
                    case 3:
                        //Toast.makeText(parent.getContext(), "Mark", Toast.LENGTH_SHORT).show();
                        usbService.changeParidad(3);
                        break;
                    case 4:
                        //Toast.makeText(parent.getContext(), "Space", Toast.LENGTH_SHORT).show();
                        usbService.changeParidad(4);
                        break;
                    default:
                        return;
                }
            case R.id.spStop:
                parent.getItemAtPosition(pos);
                // Toast.makeText(parent.getContext(), "Spdato", Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0:
                        //Toast.makeText(parent.getContext(), "1", Toast.LENGTH_SHORT).show();
                        usbService.changeStop(0);
                        break;
                    case 1:
                        //Toast.makeText(parent.getContext(), "1.5", Toast.LENGTH_SHORT).show();
                        usbService.changeStop(1);
                        break;
                    case 2:
                        //Toast.makeText(parent.getContext(), "2", Toast.LENGTH_SHORT).show();
                        usbService.changeStop(2);
                        break;
                    default:
                        return;
                }
            case R.id.spControl:
                parent.getItemAtPosition(pos);
                // Toast.makeText(parent.getContext(), "Spdato", Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0:
                        // Toast.makeText(parent.getContext(), "Xon/Xoff", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // Toast.makeText(parent.getContext(), "Hardware", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        // Toast.makeText(parent.getContext(), "None", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return;
                }

        }

    }



    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }

    public void Default_Parametros(){
        //usbService.changeBaudRate(9600);
        //usbService.changeBits(8);
        //usbService.changeStop(0);// bits 1
        //usbService.changeParidad(0);//none
        //usbService.changeControl(0);//none
    }

    @Override
    public void onResume() {
        super.onResume();
        setFilters();  // Start listening notifications from UsbService
        startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it

    }

    @Override
    public void onPause() {
        super.onPause();
        // unregisterReceiver(mUsbReceiver);
        unbindService(usbConnection);

    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }





}
