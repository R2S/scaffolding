package biz.r2s.scaffolding.meta.field.params;

import biz.r2s.scaffolding.meta.field.TypeFieldScaffold;

/**
 * Created by raphael on 06/08/15.
 */
public class ParamsFactory {
    public static  ParamsFieldScaffold factory(TypeFieldScaffold fieldScaffold) {
        ParamsFieldScaffold params = null;
        switch (fieldScaffold) {
            case CELULAR:
                params = new CelularParamsFieldScaffold();
                break;

            case CEP:
                params = new CepParamsFieldScaffold();
                break;

            case CPF:
                params = new CpfParamsFieldScaffold();
                break;

            case CHECKBOX:
                params = new CheckboxParamsFieldScaffold();
                break;

            case CNPJ:
                params = new CnpjParamsFieldScaffold();
                break;

            case DATATABLE:
                params = new DataTableParamsFieldScaffold();
                break;

            case DATE:
                params = new DateParamsFieldScaffold();
                break;

            case EMAIL:
                params = new EmailParamsFieldScaffold();
                break;
            case FILE:
                params = new FileParamsFieldScaffold();
                break;
            case HIDDEN:
                params = new HiddenParamsFieldScaffold();
                break;
            case HORA:
                params = new HoraParamsFieldScaffold();
                break;
            case INPUT:
                params = new InputParamsFieldScaffold();
                break;
            case REAIS:
                params = new ReaisParamsFieldScaffold();
                break;
            case NUMBER:
                params = new NumberParamsFieldScaffold();
                break;
            case NUMBER_PRECISION:
                params = new NumberPrecisionParamsFieldScaffold();
                break;
            case PASSWORD:
                params = new PasswordParamsFieldScaffold();
                break;
            case PERIODO:
                params = new PeriodoParamsFieldScaffold();
                break;
            case PLACA_CARRO:
                params = new PlacaCarroParamsFieldScaffold();
                break;
            case STATIC:
                params = new StaticParamsFieldScaffold();
                break;

            case SELECT2_AJAX:
                params = new Select2AjaxParamsFieldScaffold();
                break;

           case SELECT2:
                params = new Select2ParamsFieldScaffold();
                break;

            case TELEFONE:
                params = new TelefoneParamsFieldScaffold();
                break;

            case TEXT:
                params = new TextParamsFieldScaffold();
                break;
        }

        return params;
    }

}
