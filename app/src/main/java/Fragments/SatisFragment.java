package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import Responses.SozlesmeResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SatisFragment extends Fragment {
    String shipid,billid;
    private WebView webView;
    String html="<pre>PRELIMINARY INFORMATION FORM FOR CONSUMER LEGISLATION REQUIREMENTS<br /> 1. INFORMATION ABOUT THE SELLER<br /> Commercial Title:<br /> Address:<br /> Phone:<br /> Email Address:<br /><br /> 2. BUYER INFORMATION<br /> Name Surname / Title:<br /> Delivery Address:<br /> Phone:<br /> Email:<br /><br /> 3.SUBJECT<br /> Subject of this Preliminary Information Form;  Informing the purchaser in accordance with the provisions of the Law on the Protection of Consumers numbered 6502 and the Regulation on Distance Contracts published in the Official Gazette dated November 27, 2014 and numbered 29188 regarding the sale and delivery of the product or products whose qualification and sales price is specified below.<br /><br /> 4. BASIC FEATURES AND PAYMENT INFORMATION OF THE CONTRACTED PRODUCT<br /> This section describes the contract or the main features of the products.<br /><br /> Explanation            :<br /> Quantity:<br /> Total Amount:<br /> Amount including VAT:<br /> Payment:<br /> Total Amount Payable (Including VAT):<br /><br />5. DELIVERY TIME OF GOODS / SERVICE<br /> Delivery shall be made as soon as possible after the availability of the stock and the transfer of the goods to the Seller's account.  Natural disasters, weather opposition etc.  There may be delays due to force majeure.  The seller delivers the goods / services within 30 (thirty) days of the order.<br /><br /> 6. DELIVERY OF GOODS / SERVICES<br /> Delivery of the goods / service is made to the address requested by the buyer.  If the buyer requests delivery to another person and to another address from his own address, delivery shall be made in accordance with this request.  Delivery costs are the responsibility of the Buyer.  If the Seller declares that the delivery fee will be paid by the shopper or in some campaigns on the website, the delivery cost shall be borne by the Seller.  Goods / services are delivered by cargo companies.<br /><br /> For the delivery of the goods / services subject to the order, a signed copy of the distance sales contract must be delivered to the Seller and the price must be paid by the Buyer's preferred form of payment.  If the goods / services are not paid or canceled in the bank records for any reason, the Seller shall be deemed free of the obligation to deliver the goods / services.<br /><br />7. METHOD OF PAYMENT<br /> Payments can be made by credit card, EFT or wire transfer.<br /><br /> 8. VALIDITY PERIOD<br /> The prices listed and announced on the website are the sales price.  The announced prices and promises are valid until the update is made and changed.  The prices announced for a period of time shall remain valid until the end of the specified period.  However, in case of price differences that are incorrectly typed and not updated by the supplier's late notification, the current price to be notified by the Seller to the customer shall be deemed valid.  In case of errors, the difference shall be refunded if more goods / services were charged.  If the actual price of the good / service is different from the declared price, the Buyer shall be notified of the actual price.  In accordance with the customer's request, sales are realized or canceled at the actual price.<br /><br /> 9. RIGHT OF CANCELLATION<br /> Receiver;  in distant contracts regarding the sale of goods, within fourteen days from the date of delivery of the goods without any legal and criminal responsibility and without giving any reason to reject the right to withdraw from the contract.  In distance contracts for the provision of services, this period begins on the date of the contract.  If it is decided in the contract to perform the service before the expiration of fourteen days, the consumer may exercise his right of withdrawal until the date when the performance starts.  Expenses arising from the exercise of the right of withdrawal belong to the seller.<br />In order to exercise the right of withdrawal, the Buyer shall be notified to the Seller by fax, telephone or e-mail within the fourteen-day period, and within the framework of the provisions of Article 4 of the Distance Sale Agreement of the goods / services and in accordance with the preliminary information published on the website.  the packaging and its contents shall not be damaged during the testing of the goods and shall be resaleable by the Seller.  The return procedures within the scope of Right of Withdrawal are included in the Distance Sales Contract.  In the event that this right is exercised, the original invoice for the goods / services delivered to the 3rd party or the Purchaser is obligatory.  Within 14 (fourteen) days following the receipt of the notice of right of withdrawal, the cost of goods and services and delivery costs are returned to the Buyer and the buyer is obliged to return the goods / services within 10 (ten) days.  If the original invoice is not sent, VAT and other legal obligations, if any, cannot be returned to the Buyer.  The delivery price of the goods / services returned with the right of withdrawal shall be borne by the Buyer.<br /><br /> If there is a decrease in the value of the goods due to the buyer's defect or if the return becomes impossible, the Buyer is obliged to compensate the Seller's loss at the defect rate. Payments can be made using credit card, EFT or wire transfer methods.<br /><br />10. GOODS / SERVICES THAT THE RIGHT TO WITHDRAWAL CANNOT BE USED<br /> The goods / services that cannot be returned in terms of their nature are the goods / services that are rapidly deteriorating and exceed the expiry date, disposable goods / services, all kinds of copyable software and programs.  In addition, in order to use the right of withdrawal in all kinds of software and programs, DVD, DIVX, VCD, CD, MD, videotapes, computer and stationery consumables (toner, cartridge, ribbon and the like) and cosmetic materials, the packaging of the goods / services has not been opened,  are required.<br /><br /> a) Contracts for goods or services whose price varies due to fluctuations in financial markets and which are not under the control of the seller or provider.<br /><br /> b) Contracts relating to goods prepared in line with the wishes or personal needs of the consumer.<br /><br /> c) Contracts for the delivery of goods which may be quickly degraded or that may expire.<br /><br /> &ccedil;) From the goods whose protective elements such as packaging, tape, seal, package have been opened after delivery;  contracts for the delivery of non-refundable health and hygiene requirements.<br /><br /> d) Contracts for goods which, after delivery, are intermixed with other products and which are inherently impossible to be segregated.<br /><br /> e) Contracts on books, digital content and computer consumables provided in the material environment in case the protective elements such as packaging, tape, seal, package are opened after the delivery of the goods.<br /><br /> f) Contracts for the delivery of periodicals such as newspapers and magazines other than those provided under the subscription agreement.<br /><br /> g) Contracts to be made at a certain date or period for the purposes of accommodation, transport of goods, car rental, food and beverage supply and leisure time for recreational or recreational purposes.<br /><br /> ğ) Contracts for services rendered in electronic environment or royalties delivered immediately to consumers.<br /><br /> h) Contracts for services commenced with the consent of the consumer before the right of withdrawal expires.<br /><br />11. VALIDITY<br /> After this preliminary information form has been read and accepted by the Purchaser electronically, the process of establishing a Distance Sales Contract will be started.<br /> 12. AUTHORIZED COURT<br /> Consumer;  complaints and objections, T.C.  Consumer problems in the place where the consumer buys goods or services within the monetary limits determined by the Ministry of Customs and Trade in December of each year or where the residence is located may be brought to the arbitral tribunal or consumer court.<br /> 13. FINAL PROVISIONS<br /> In the event that the documents and information provided for the order are found to be incomplete, fraudulent and / or inaccurate, or if there is any suspicion that the order has been made for the purpose of bad intentions and / or for the purpose of gaining,  reserves the right to stop and / or cancel the necessary investigations.  In the event of cancellation, the refund process for payment can be made again, provided that the Buyer is notified.<br />14. EXCEPTION<br /> The provisions of the Article 6502 on Consumer Protection arising from the Law on Consumer Protection in this preliminary information form shall be valid only in cases where the buyer is the Consumer;  In cases where the buyer does not comply with the definition of Consumer in the Law no. 6502, the relevant articles shall not be valid between the parties.<br />Receiver;  6502 S.K. M. 48, f.2 and Mes.  Promise.  Direction.  It accepts, undertakes and declares that it has read and informed the Preliminary Information in accordance with Articles 5, 6 and 7 and has given the necessary confirmation in electronic medium.</pre>";

    public SatisFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_satis, container, false);
      //  webView =view.findViewById(R.id.web_view);
        //  progressBar = view.findViewById(R.id.progress_circular);



      webView=view.findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setMinimumFontSize(45);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webView.loadData(html,"text/html; charset=utf-8","UTF-8");

        return view;

    }

    }





