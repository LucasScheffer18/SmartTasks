package com.example.smarttasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class TaskUrgenciaComparator implements Comparator<Task> {
    @Override
    public int compare(Task t1, Task t2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            Date d1 = sdf.parse(t1.getData());
            Date d2 = sdf.parse(t2.getData());

            return d1.compareTo(d2); // Mais urgente = data mais próxima

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
