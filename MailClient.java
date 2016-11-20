/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * 
 * @author David J. Barnes and Michael KÃ¶lling
 * @version 2011.07.31
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    private boolean deVacaciones;  //--------permite dar una respuesta automáticamente, restAutomaticForHoliday();
    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        deVacaciones = false;  //------utilizado en restAutomaticForHoliday() y en mailAutomaticDeVacaciones().
    }

    /**
     * 0171_1) método  que permita saber desde un cliente de correo electrónico cuántos emails tenemos en el servidor para nosotros
     *  dicha información se muestre por pantalla. Importante: los correos no deben ser descargados
     * del servidor.
     */
    public void numEmails(){
        int totalEmails = server.howManyMailItems(user);
        System.out.println("Tienes " + totalEmails + " correos en el servidor.");
    }

    /**
     * 0171_2) método que habilite o deshabilite la respuesta automática. En caso de estar habilitada, cada vez que 
     * invocamos al método getNextMailItem se lleva a cabo la respuesta automática. De inicio, la respuesta automática está 
     * desactivada. 
     */
    public void restAutomaticForHoliday(){//
        deVacaciones = true;
    }

    /**
     * 0171_2) método que permit configurar el mensaje y el asunto que se enviará cuando se
     *lleve a cabo una respuesta automática.
     */
    private void mailAutomaticDeVacaciones(){
        int numE = server.howManyMailItems(user);  // 1º recupero el mensaje recibido.
        if(numE > 0 && deVacaciones == true){
            MailItem item = server.getNextMailItem(user);  // 1º recupero el mensaje recibido.
            sendMailItem(item.getFrom(), item.getSubject(), "\n Estamos de vacaciones.  \n"
                +item.getMessage()); 
        }//2º lo devuelvo con la respuesta de mensaje  automático.
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        mailAutomaticDeVacaciones();
        MailItem item = server.getNextMailItem(user);
        
        deVacaciones = false;
        return item;

    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String subject, String message)
    {
        MailItem item = new MailItem(user, to, subject, message);
        server.post(item);
    }
}