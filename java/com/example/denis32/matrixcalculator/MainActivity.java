package com.example.denis32.matrixcalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Иды пунктов контекстных меню
    final int CONTEXT_SHOW_CALC = 1;
    final int CONTEXT_WITHOUT_PADDING = 2;
    final int CONTEXT_WITH_PADDING = 3;
    final int CONTEXT_CLEAR = 4;
    final int CONTEXT_RANDOM = 5;

    // Общие переенные
    Integer current_matrix = 3;
    TextView text_message;
    Button button_calculate;
    TableLayout content_input_matrix;

    // Результат вычислений
    int answer = 0;
    String stringOfAnswer = "Не посчитано!";

    // Поля для ввода значений матрицы
    EditText input11;
    EditText input12;
    EditText input13;
    EditText input21;
    EditText input22;
    EditText input23;
    EditText input31;
    EditText input32;
    EditText input33;

    LinearLayout.LayoutParams lParamsForEditBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Главное меню
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Элементы на экране
        text_message = (TextView) findViewById(R.id.text_message);
        button_calculate = (Button)findViewById(R.id.button_calculate);
        content_input_matrix = (TableLayout) findViewById(R.id.content_input_matrix);

        // Input matrix
        input11 = (EditText)findViewById(R.id.input11);
        input12 = (EditText)findViewById(R.id.input12);
        input13 = (EditText)findViewById(R.id.input13);

        input21 = (EditText)findViewById(R.id.input21);
        input22 = (EditText)findViewById(R.id.input22);
        input23 = (EditText)findViewById(R.id.input23);

        input31 = (EditText)findViewById(R.id.input31);
        input32 = (EditText)findViewById(R.id.input32);
        input33 = (EditText)findViewById(R.id.input33);
        // End of input matrix

        // Contex Menu for text and TableLayout
        registerForContextMenu(content_input_matrix);
        registerForContextMenu(text_message);

        button_calculate.setOnClickListener(this);

        lParamsForEditBox = (LinearLayout.LayoutParams) input11.getLayoutParams();
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
        if (id == R.id.matrix_2x2 && current_matrix == 3) {
            current_matrix = 2;

            input13.setVisibility(View.GONE);
            input23.setVisibility(View.GONE);

            input31.setVisibility(View.GONE);
            input32.setVisibility(View.GONE);
            input33.setVisibility(View.GONE);

            Toast.makeText(this, "Выбрана матрица 2х2", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.matrix_3x3 && current_matrix == 2) {
            current_matrix = 3;

            input13.setVisibility(View.VISIBLE);
            input23.setVisibility(View.VISIBLE);

            input31.setVisibility(View.VISIBLE);
            input32.setVisibility(View.VISIBLE);
            input33.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Выбрана матрица 3х3", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.finish)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            // Кнопка ВЫЧИСЛИТЬ
            case R.id.button_calculate: {

                // Проверка на то, пустые ли поля
                if (input11.getText().length() == 0 || input12.getText().length() == 0 ||
                    input21.getText().length() == 0 || input22.getText().length() == 0 || (current_matrix == 3 &&
                        (input13.getText().length() == 0 || input23.getText().length() == 0 ||
                        input31.getText().length() == 0 || input32.getText().length() == 0 || input33.getText().length() == 0) ) ) {
                    Toast.makeText(this, "Заполните пустые поля либо выберите другой тип матрицы!", Toast.LENGTH_LONG).show();
                } else {

                    int element11 = Integer.parseInt(input11.getText().toString());
                    int element12 = Integer.parseInt(input12.getText().toString());
                    int element21 = Integer.parseInt(input21.getText().toString());
                    int element22 = Integer.parseInt(input22.getText().toString());

                    if (current_matrix == 3) { // Если вычисляем матрицу 3х3
                        int element13 = Integer.parseInt(input13.getText().toString());
                        int element23 = Integer.parseInt(input23.getText().toString());
                        int element31 = Integer.parseInt(input31.getText().toString());
                        int element32 = Integer.parseInt(input32.getText().toString());
                        int element33 = Integer.parseInt(input33.getText().toString());

                        answer = element11 * element22 * element33 +
                                element12 * element23 * element31 +
                                element21 * element32 * element13 -
                                element31 * element22 * element13 -
                                element12 * element21 * element33 -
                                element11 * element23 * element32;

                        stringOfAnswer = "Расчеты: " + element11 + "*" + element22 + "*" + element33 + " + " +
                                element11 + "*" + element22 + "*" + element33 + " + " +
                                element11 + "*" + element22 + "*" + element33 + " - " +
                                element11 + "*" + element22 + "*" + element33 + " - " +
                                element11 + "*" + element22 + "*" + element33 + " - " +
                                element11 + "*" + element22 + "*" + element33 + " = " + answer;

                        text_message.setText("Матрица: \n" +
                                element11 + " " + element12 + " " + element13 + "\n" +
                                element21 + " " + element22 + " " + element23 + "\n" +
                                element31 + " " + element32 + " " + element33 + "\n" +
                                "Определитель матрицы = " + answer);
                    } else // Если вычисляем матрицу 2х2
                    {
                        answer = element11 * element22 - element12 * element21;

                        stringOfAnswer = "Расчеты: " + element11 + "*" + element22 + " - " +
                                element12 + "*" + element21 + " = " + answer;

                        text_message.setText("Матрица: \n" +
                                element11 + " " + element12 + "\n" +
                                element21 + " " + element22 + "\n" +
                                "Определитель матрицы = " + answer);
                    }
                    Toast.makeText(this, stringOfAnswer, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.text_message:
                menu.add(0, CONTEXT_SHOW_CALC, 0, "Показать расчёт");
                break;
            case R.id.content_input_matrix:
                menu.add(0, CONTEXT_WITHOUT_PADDING, 0, "Убрать отступы");
                menu.add(0, CONTEXT_WITH_PADDING, 0, "Добавить отступы");
                menu.add(0, CONTEXT_CLEAR, 0, "Отчистить поля");
                menu.add(0, CONTEXT_RANDOM, 0, "Заполнить случайными значениями");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {

            // text_message
            case CONTEXT_SHOW_CALC:
                Toast.makeText(this, stringOfAnswer, Toast.LENGTH_LONG).show();
                break;

            // content_input_matrix
            case CONTEXT_WITHOUT_PADDING:
                lParamsForEditBox.setMargins(0, 0, 0, 0);

                input11.setLayoutParams(lParamsForEditBox); input12.setLayoutParams(lParamsForEditBox); input13.setLayoutParams(lParamsForEditBox);
                input21.setLayoutParams(lParamsForEditBox); input22.setLayoutParams(lParamsForEditBox); input23.setLayoutParams(lParamsForEditBox);
                input31.setLayoutParams(lParamsForEditBox); input32.setLayoutParams(lParamsForEditBox); input33.setLayoutParams(lParamsForEditBox);

                Toast.makeText(this, "Отступы убраны!", Toast.LENGTH_LONG).show();
                break;

            case CONTEXT_WITH_PADDING:
                lParamsForEditBox.setMargins(15, 15, 15, 15);

                input11.setLayoutParams(lParamsForEditBox); input12.setLayoutParams(lParamsForEditBox); input13.setLayoutParams(lParamsForEditBox);
                input21.setLayoutParams(lParamsForEditBox); input22.setLayoutParams(lParamsForEditBox); input23.setLayoutParams(lParamsForEditBox);
                input31.setLayoutParams(lParamsForEditBox); input32.setLayoutParams(lParamsForEditBox); input33.setLayoutParams(lParamsForEditBox);

                Toast.makeText(this, "Отступы добавлены!", Toast.LENGTH_LONG).show();
                break;

            case CONTEXT_CLEAR:
                input11.setText(""); input12.setText(""); input13.setText("");
                input21.setText(""); input22.setText(""); input23.setText("");
                input31.setText(""); input32.setText(""); input33.setText("");

                Toast.makeText(this, "Поля отчищены!", Toast.LENGTH_LONG).show();
                break;

            case CONTEXT_RANDOM:

                input11.setText(randInt()); input12.setText(randInt());
                input21.setText(randInt()); input22.setText(randInt());

                if(current_matrix == 3) {
                    input13.setText(randInt());
                    input23.setText(randInt());
                    input31.setText(randInt());
                    input32.setText(randInt());
                    input33.setText(randInt());
                }
                Toast.makeText(this, "Поля заполнены случайными значениями!", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public String randInt()
    {
        Random rand = new Random();
        return Integer.toString( (rand.nextInt(12) - 6) );
    }
}