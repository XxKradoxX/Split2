package com.example.kent.split2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowCaptureActivity extends AppCompatActivity {

    public static ArrayList<Line> totalComponents = new ArrayList<Line>();
    public static ArrayList<List<Line>> lines = new ArrayList<List<Line>>();
    public static List<Product> itemsBought = new ArrayList<>();
    Reciept mReciept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_capture);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        byte[] b = extras.getByteArray("capture");

        if(b!=null) {
            ImageView imageView = findViewById(R.id.imageCaptured);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            Bitmap rotateBitmap = rotate(decodedBitmap);

            imageView.setImageBitmap(rotateBitmap);
            getTextFromImage(rotateBitmap);

        }

        Button mSend = findViewById(R.id.send);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> data = new ArrayList<>();

                for (int i =0; i < mReciept.getItems().size(); i++) {
                    System.out.println("shiba"+mReciept.getItems().get(i).getName());
                    data.add(mReciept.getItems().get(i).getName());
                    data.add(String.valueOf(mReciept.getItems().get(i).getPrice()));
                }

                Intent intent = new Intent(getApplicationContext(), ReviewRecieptActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);

            }
        });

    }

    private Bitmap rotate(Bitmap decodedBitmap) {
        int w = decodedBitmap.getWidth();
        int h = decodedBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        return Bitmap.createBitmap(decodedBitmap,0,0,w,h,matrix,true);
    }

    private void getTextFromImage(final Bitmap picture) {

        TextRecognizer textRecognizer = new TextRecognizer.Builder(this.getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Toast.makeText(this.getApplicationContext(), "Could not get the Text", Toast.LENGTH_LONG).show();
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(picture).build();

            SparseArray<TextBlock> items = textRecognizer.detect(frame);


            for (int i = 0; i <items.size(); i++) {
                Point point[] = new Point[4];
                TextBlock myItem = items.valueAt(i);
                List<Line> components = (List<Line>) myItem.getComponents();

                for (int a = 0; a < components.size();a++) {
                    totalComponents.add((Line) components.get(a));
                }
            }

        }

        Runnable r = new Runnable() {
            @Override
            public void run() {
                List<Integer> indexHistory = new ArrayList<Integer>();
                List<Line> history=new ArrayList<Line>();
                indexHistory.clear();

                for (int i = 0; i < totalComponents.size(); i++) {
                    if (!history.contains(totalComponents.get(i))) {
                        history.add(totalComponents.get(i));
                        List<Line> currentLine = new ArrayList<Line>();
                        Line line_1 = totalComponents.get(i);
                        currentLine.add(line_1);
                        int left_1 = line_1.getCornerPoints()[0].x;
                        int right_1 = line_1.getCornerPoints()[1].x;
                        int yMid_1 = line_1.getCornerPoints()[3].y - (line_1.getCornerPoints()[3].y - line_1.getCornerPoints()[0].y) / 2;

                        for (int a = i + 1; a < totalComponents.size(); a++) {
                            Line line_2 = totalComponents.get(a);
                            int left_2 = line_2.getCornerPoints()[0].x;
                            int right_2 = line_2.getCornerPoints()[1].x;
                            int yMax_2 = line_2.getCornerPoints()[0].y;
                            int yMin_2 = line_2.getCornerPoints()[3].y;
                            if (!((left_1 < right_2 && left_1 > left_2) || (right_1 > left_2 && right_1 < right_2)) &&
                                    (yMid_1 < yMin_2 && yMid_1 > yMax_2) &&
                                    (!history.contains(line_2))) {
                                currentLine.add(line_2);
                                history.add(line_2);
                            }
                        }
                        lines.add(currentLine);
                    }

                }
                for (int i = 0; i < lines.size(); i++) {
                    List<Line> line = lines.get(i);

                    String lineString = "";
                    for (int a = 0; a < line.size(); a++) {
                        lineString += " " + line.get(a).getValue();
                    }
                }


                // Scans through text to find product name and price
                for (int i = 0; i < lines.size(); i++) {
                    List<Line> line = lines.get(i);

                    String lineString = "";
                    for (int a =0; a < line.size(); a++) {
                        lineString += " " + line.get(a).getValue();
                    }

                    String priceString = "";
                    String name = "";

                    if (checkForPrice(lineString)) {
                        System.out.println("Contains { " + lineString);
                        int a = 0;
                        int b = 0;
                        boolean isPrice = false;
                        boolean priceFound = false;

                        for (a = 0; a < lineString.length() && !isPrice; a++) {

                            if (Character.isDigit(lineString.charAt(a))) {
                                for (b = a;  b < lineString.length(); b++) {
                                    if (!priceFound) {
                                        if (!Character.isDigit(lineString.charAt(b)) && !isPrice && lineString.charAt(b) != '.'){
                                            isPrice = false;
                                            break;
                                        }
                                        if (lineString.charAt(b) == '.' && !isPrice) {
                                            isPrice = true;
                                        } else if (!Character.isDigit(lineString.charAt(b)) && lineString.charAt(b) != ',' && isPrice) {
                                            priceFound = true;
                                            break;
                                        }
                                    }
                                }
                                for (int d = a; d < b ; d++) {
                                    if (lineString.charAt(d) != ',') {
                                        priceString = priceString + lineString.charAt(d);
                                    }
                                }
                                double price = Double.parseDouble(priceString);

                                if (a-1 > lineString.length()/2) {
                                    for (int c = 0; c < a-1; c++) {
                                        if (c != i) {
                                            name += lineString.charAt(c);
                                        }
                                    }
                                } else {
                                    for (int c = b; c < lineString.length(); c++) {
                                        name += lineString.charAt(c);
                                    }
                                }
                                Product p = new Product(name,price,line);
                                itemsBought.add(p);
                                break;
                            }
                        }
                    }
                }
                mReciept = new Reciept(itemsBought,picture);

            }
        };

        Thread thread = new Thread(r);
        thread.start();

    }

    public static boolean checkForPrice(String s) {

        if (s.matches(".*\\d+.*")) {

            for (int i = 0; i < s.length()-2; i++) {
                if (Character.isDigit(s.charAt(i))) {
                    if (s.charAt(i+1) == '.' && Character.isDigit(s.charAt(i+2))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
