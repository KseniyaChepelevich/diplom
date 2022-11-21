package ru.iteco.fmhandroid.ui.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeviceHelper {
    /**
     * Выполняет bash скрипт через терминал с обработкой exception
     * Не гарантирует полного получения результата выполнения команды, так как нет обратного Callback
     * Подходит для выполнения скрипта без возвращения результата
     * @param command bash команда
     * @return результат скрипты
     */
    public static String executeBash(String command) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(command); //получаем инстанс терминала и посылаем скрипт
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        final String[] message = {""}; //массив с 1 элементом для записи строк из терминала

        new Thread(()->{//запускаем новый поток чтобы не было сообщения "Процесс не отвечает", в случае если команда будет выполнятся бесконечно
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));//получаем поток информации
            String line = null;
            while (true){
                try {
                    if ((line = input.readLine()) == null) {//читаем строки пока они есть
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                message[0] += line + "\n"; //записываем строки в первый элемент массива
            }
            System.out.println(message[0]);//выводим в консоль для дебагинга
        }).start();//стартуем поток
        try {
            p.waitFor();//ждем завершения потока
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return message[0];
    }
}
