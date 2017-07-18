package com.pratikbutani.pdf.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.pratikbutani.pdf.FileUtils;
import com.pratikbutani.pdf.R;
import com.pratikbutani.pdf.permission.PermissionsActivity;
import com.pratikbutani.pdf.permission.PermissionsChecker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.pratikbutani.pdf.LogUtils.LOGE;
import static com.pratikbutani.pdf.permission.PermissionsActivity.PERMISSION_REQUEST_CODE;
import static com.pratikbutani.pdf.permission.PermissionsChecker.REQUIRED_PERMISSION;

public class MainActivity extends AppCompatActivity {

    Context mContext;

    PermissionsChecker checker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checker = new PermissionsChecker(this);

        createPdf(FileUtils.getAppPath(mContext) + "123.pdf");

        /**
         *
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checker.lacksPermissions(REQUIRED_PERMISSION)) {
                    PermissionsActivity.startActivityForResult(MainActivity.this, PERMISSION_REQUEST_CODE, REQUIRED_PERMISSION);
                } else {
                    createPdf(FileUtils.getAppPath(mContext) + "123.pdf");
                }
            }
        });
    }

    public void createPdf(String dest) {

        if (new File(dest).exists()) {
            new File(dest).delete();
        }

        try {
            /**
             * Creating Document
             */
            Document document = new Document();

            // Location to save
            PdfWriter.getInstance(document, new FileOutputStream(dest));

            // Open to write
            document.open();

            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Android School");
            document.addCreator("Pratik Butani");

            /***
             * Variables for further use....
             */
            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 26.0f;

            /**
             * How to USE FONT....
             */
            BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);

            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDetailsTitleChunk = new Chunk("Order Details", mOrderDetailsTitleFont);
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(mOrderDetailsTitleParagraph);

            // Fields of Order Details...
            // Adding Chunks for Title and value
            Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderIdChunk = new Chunk("Order No:", mOrderIdFont);
            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
            document.add(mOrderIdParagraph);

            Font mOrderIdValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderIdValueChunk = new Chunk("#123123", mOrderIdValueFont);
            Paragraph mOrderIdValueParagraph = new Paragraph(mOrderIdValueChunk);
            document.add(mOrderIdValueParagraph);

            // Adding Line Breakable Space....
            document.add(new Paragraph(""));
            // Adding Horizontal Line...
            document.add(new Chunk(lineSeparator));
            // Adding Line Breakable Space....
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Font mOrderDateFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderDateChunk = new Chunk("Order Date:", mOrderDateFont);
            Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
            document.add(mOrderDateParagraph);

            Font mOrderDateValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderDateValueChunk = new Chunk("06/07/2017", mOrderDateValueFont);
            Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
            document.add(mOrderDateValueParagraph);

            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            // Fields of Order Details...
            Font mOrderAcNameFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderAcNameChunk = new Chunk("Account Name:", mOrderAcNameFont);
            Paragraph mOrderAcNameParagraph = new Paragraph(mOrderAcNameChunk);
            document.add(mOrderAcNameParagraph);

            Font mOrderAcNameValueFont = new Font(urName, mValueFontSize, Font.NORMAL, BaseColor.BLACK);
            Chunk mOrderAcNameValueChunk = new Chunk("Pratik Butani", mOrderAcNameValueFont);
            Paragraph mOrderAcNameValueParagraph = new Paragraph(mOrderAcNameValueChunk);
            document.add(mOrderAcNameValueParagraph);

            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            document.close();

            Toast.makeText(mContext, "Created... :)", Toast.LENGTH_SHORT).show();

            FileUtils.openFile(mContext, new File(dest));

        } catch (IOException | DocumentException ie) {
            LOGE("createPdf: Error " + ie.getLocalizedMessage());
        } catch (ActivityNotFoundException ae) {
            Toast.makeText(mContext, "No application found to open this file.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PermissionsActivity.PERMISSIONS_GRANTED) {
            Toast.makeText(mContext, "Permission Granted to Save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Permission not granted, Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
