package burp;

public class BurpExtender implements IBurpExtender, IHttpListener{

    private IExtensionHelpers helpers;
    @Override
    public void registerExtenderCallbacks( IBurpExtenderCallbacks callbacks)
    {
        helpers = callbacks.getHelpers();

        // set our extension name
        callbacks.setExtensionName("Traffic redirector");

        // register ourselves as an HTTP listener
        callbacks.registerHttpListener(this);
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo)
    {
        final String HOST_TO = "napmatest.infusionsoft.com";
        final String HOST_FROM = "a48.infusiontest.com";
        // only process requests
        if (messageIsRequest)
        {
            // get the HTTP service for the request
            IHttpService httpService = messageInfo.getHttpService();

            System.out.println("HOST_FROM=" + httpService.getHost());

            // if the host is HOST_FROM, change it to HOST_TO
            if (HOST_FROM.equalsIgnoreCase(httpService.getHost())) {
                messageInfo.setHttpService(helpers.buildHttpService(HOST_TO, httpService.getPort(), httpService.getProtocol()));
            }


        }
    }


}