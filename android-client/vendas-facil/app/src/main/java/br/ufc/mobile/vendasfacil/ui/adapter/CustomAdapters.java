package br.ufc.mobile.vendasfacil.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import br.ufc.mobile.vendasfacil.model.enums.Unidade;

public class CustomAdapters {

    public class SpinnerUnidade extends ArrayAdapter<Unidade> {

        public SpinnerUnidade(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }
}
