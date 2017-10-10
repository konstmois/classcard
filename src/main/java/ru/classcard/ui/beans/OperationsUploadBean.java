package ru.classcard.ui.beans;

import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import ru.classcard.services.operations.OperationsUploadService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import static javax.faces.context.FacesContext.getCurrentInstance;

@ViewScoped
@ManagedBean(name = "operationsUpload")
public class OperationsUploadBean {

    private static final Logger LOGGER = Logger.getLogger(OperationsUploadBean.class);

    @ManagedProperty(value = "#{cardBean}")
    private CardBean cardBean;

    @ManagedProperty(value = "#{uploadService}")
    private OperationsUploadService uploadService;



    public void upload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        if(file != null) {
            try {
                uploadService.uploadOperations(cardBean.getCard(), file.getInputstream());
                addSuccessMessage(file);
            } catch (Exception ex) {
                LOGGER.error("Filename = " + file.getFileName(), ex);
                addErrorMessage();
            }
        }
    }

    private void addSuccessMessage(UploadedFile file) {
        FacesMessage message = new FacesMessage("Выписка загружена.", file.getFileName());
        getCurrentInstance().addMessage(null, message);
    }

    private void addErrorMessage() {
        FacesMessage message = new FacesMessage("Ошибка загрузки.", "Некорректный формат выписки. \n" +
                                                                    "Проверьте заполнение полей и номер карты,\n" +
                                                                    "повторите загрузку еще раз.\n");
        getCurrentInstance().addMessage(null, message);
    }

    public void setCardBean(CardBean cardBean) {
        this.cardBean = cardBean;
    }

    public void setUploadService(OperationsUploadService uploadService) {
        this.uploadService = uploadService;
    }

}
