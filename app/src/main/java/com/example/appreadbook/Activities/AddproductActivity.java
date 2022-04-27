package com.example.appreadbook.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.ApiClient;
import com.example.appreadbook.Adapters.CategoryselectAdapter;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityAddproductBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddproductActivity extends AppCompatActivity {
    List<Categorydata> categorydatalist;
    ActivityAddproductBinding binding;
    Spinner listtype;
    private String encodedImage="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkz\n" +
            "ODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P/2wBDARESEhgVGC8aGi9jQjhCY2NjY2Nj\n" +
            "Y2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P/wAARCADUAJYDASIA\n" +
            "AhEBAxEB/8QAGwAAAgMBAQEAAAAAAAAAAAAAAwQAAgUBBgf/xAA6EAACAQMDAgQDBwMDBAMBAAAB\n" +
            "AgMABBESITEFQRMiUWEUcYEGMpGhsdHwI0LBFeHxM1JiciQ0Q4L/xAAaAQADAQEBAQAAAAAAAAAA\n" +
            "AAABAgMEAAUG/8QAJxEAAgICAgIBBAIDAAAAAAAAAAECERIhAzFBUSIEEzJxFGEzQvD/2gAMAwEA\n" +
            "AhEDEQA/ANyF3JHmNbNu3hxBnbnjNYdu4Q6m+6Kaa7aQEkY2wPaj4PS5uNydI2DKPC8RdxSE904z\n" +
            "91h7ih2FwWV4HP3gdPzrNnuDE5Ge+N+1Lmktk+Lg+TRaedCxymPlQHaFu+KkjLIM9jScykcUbs3K\n" +
            "NI5Pp7MDScinuK7KSBk0s0hzTpEZyI5oDH3qzS53zvXEUSaizBVAyT6fSntRVszP5OkDbYkbfShs\n" +
            "d9qkbeI+nUuc88CuSMni+HHlj+tFTRJxdWcP5VFUyHA/OuMrZxgk+29djJySMAVRU+iMnRxoXHv2\n" +
            "rqHffZu+e9ELkAnOPeli+iZSRqVt+MGpY4O10dlkqY7GkkhwgY99hmtSL7M3k90LSXRDIU8Q6znC\n" +
            "5xnbO/17Vu9Aitl+yzvPIJI5G1yBORuPL+Q/Gt7f4/8A+uMCL/rd+fu/5qL5m3o7FI+SXdtJazGK\n" +
            "VGRxyrDBqUbqt5Nd30k876pG5bAGcDHb5VK1wbcU2Z56dHrvE8VwFBwKO2sJS1k4Ce+aLPMAlZD6\n" +
            "JloJSGGdiDQuowsCXByG3oaSgf8AtTbt4ltnGdO1M4KSpgTxaZjR3LRtgnajrIshAG3ypS7hOvKA\n" +
            "kH07VwP8KmWOWPb0qEOOWVIrKcaDXig5IIYDk/TOaQliZc5Ug8bjiobks2xzRWmZ7YsclI2GwOM5\n" +
            "z+1Wly06M741IUWPIJbZRycZxSk1wTEUUaVDZA+Yx/j860nlRrKXTzx7c5/xWJKdscUjnkyMoqEf\n" +
            "2cWTQWI5PvUScx5KE6zsTQjXIygY61LD2OMUTM2OW+oAsx1uxwN+1MeDIBgk7DPlXVj6ilElhXzJ\n" +
            "Fg+pY7U7Zsk7f1HVVXckE7+gxjv6CqLkcUScMmLys8btG4KsOQRgilyzB+DgHGxwc/zNbE9sBDHI\n" +
            "ZAV2aPVuAcDYfj+VBmtXhU+OoMjtkKCeMc9sc8YofyIzVHPgnB2er+zEaTfZa4jeVYlaY5dtgNlp\n" +
            "646xZRygtcz3Mv3QluWRMjjvvnPqazfs/wBNgm6TJLM8vhLL/wBEy6UJwNz+P5d69HBaQWUqi3hS\n" +
            "NAvmYIM9+WPI9vl2rPJXLQbpbPm/U2WW+mLQ+F52/pcaN+PpUofXpNXVrpkfUjTOQVOQRk1K38co\n" +
            "uK2ZZp5M9OG70OZg22aG0oUe9JzXO+1Silez3pypDmnLjBHvWlZ4UMruuGGDvWCGKLrkfSKrHeSS\n" +
            "HCggdzVMuKK2xI5T0a1xcRQllgUbctWFeyeKSd9XOaaupAoDMdOrcY5NIOSRjGFPbn8aL51OOEEL\n" +
            "yQwdsWk1QsQeQcZByKJb3CklHz5hgb7ZoxIeLQQCKSmieFtacA5+VZZ8db8E4816LxsdUkYBJYFR\n" +
            "+v8Aik5sg4OCRtt3pu4QJHFcxElZSeVIAOBke+5I98Uo2lgSzENz7GlSJy5MlYEgk1CoLHB2FdUg\n" +
            "NkjI9K5k755NOiTI5XAC525pq0i/omeNz4iNjR/3ew70vCdEquDgqQa0epXFvNCpgRNStuyjBPz9\n" +
            "aWV2kvI0UmnJ+CkcspOkKr6j/TLP5V7afnVSz3jCORxF4TDUV8xOOO+5+oq1tdoltJrjBON1Gytw\n" +
            "Nxx39KzVAMmrAABzgGujHbTQJy0mn2fUfslDLa9PVHlR4pWLxnlm2AzsSABj8T+PY+rK/Q7y9uUS\n" +
            "6jimKhWUKCMrjbf1rx3ResTQwy20MIE1wQgnGS6qeQP9v2xpde6Td9L6ebZL8Pbu2rwgCrO3fYZy\n" +
            "AAOTipbToXT2ef6lcQ3t9LOumBWbIjVBhR2AxipWVMSrFSBkeoqVoSSRFtnrGelWYFZGz5lGRTE0\n" +
            "REJIbz+lJCJySHVhqXVgkjbscUZTUdHpSdgTK7sWc5NNwpcYUrHIQ33Tg4rkduqqzqhZVB1EjIHz\n" +
            "os126TlFYMqEjnIP17/Os6UpvQ65VEu1s4gaWSTEi5wvOfTegw27yFi2Ao3AG+aJbyma6RZHwrHS\n" +
            "fQZpqBTDdmFjhlPlJ7HkfX/NXUHxxtgv7jsW8lvP4blcodweD7fv8jSHUmAuNABjUY1DOcnB3xn0\n" +
            "p29Sa5eWRNwGERyMc5I/Q/hWSY5JAspXWGfJOck/OpKTlticlR1Evqje1EKas+MxUHc4IHP4UK5V\n" +
            "EYpGDj1Pfb981ZUTzhSTpXj6ilnbLGqJGRP0WjgaU+Ubnj0/GhspDEEEEbEEcU1AxURuy6ow2wHG\n" +
            "fehSBnmdmOWZiTnnmnSA2DUbiusmDVwh9Kae31QrIB7GtEONtEJTpiZGVwcfWuaQBjFFaIjtQzkc\n" +
            "10uNoXKz1/2O8S1tZrrQrRlvCjChdbSHGwONhjc/TnenOr36dDkE1ory3dzAG8WWTWEB9P8AuO3J\n" +
            "9B615fo3WPgJlMqGaEBv6fiFRlhgkEcHHevSJ1CbqnS7temJa2yJEfEjYlpWjCkckbjfHtXnzi1L\n" +
            "Zoi00eCmBeQsTuTUo8kYLnFStCWiTPQyurKQQDn2oMj63LtpLbbkb4AAA+Wwrk2V2zS7E9sk+g3q\n" +
            "mCey85sKSrFde+nj+fztUMkSjZRj0pbW5OAp4zxV41Vhlm39KbFLoVSGYFBJdl54yeBRriYA+KFD\n" +
            "N31AGlvGI2zv+tWWVPDZTp1ZznO49aOKfY6m10Et52WG7hmdIxOA3iZJ3Gc/iGJ39Ka6q4mgVXVA\n" +
            "6akOkbFdsHnGc6txWQ8wRvIckdxRvjWuLUwyLECFxrIOW/PGce2/fJrNyQUeh4tzdMyw7Fjv2wf1\n" +
            "ochGsZ70SWGWJ/PGytzgihpGZJiuVXfknAArkScWpBFmIQRgAoG1b8ntR7omaQSgAKVVRjtgAY/K\n" +
            "lJGjU6YsnH9x7/SiRyHT/wCJ59qrESTGreNmIGM74xXo+mdLa4ikhMZBIyNu4/hrDtQCM5xgbH1r\n" +
            "0vSuoGyXUGDZ7Zr0IXh8ezFy7ezJ6l074XKY845rAnQ5xjFer67e+LMxIXDbjbtXm5HDygMuc984\n" +
            "ruV/BN9i8W2MdF+z8/VvFaNkVYgGbVnf5YBzXp7X7IyQ2lysFzIs+sxqxJRHTAzkYye49K1fswsM\n" +
            "fSI2iMOTHl/CXz8nn19tu3etFAkzKCJBJbtr0vzuCBvx3PB7V4spZPZ6CWJ8r63ar03qUtqJlnEZ\n" +
            "A1gY3wMj6Hb6VKB1oTt1GZrlSkzOWdWGkgnfjtzUqkHoWS2aZIA3G5oDyDttn0rkzaT52wKWaZAQ\n" +
            "QpJHqdq1Wl2CVsZDMSMcnfLVxnwcO4yOwFL+I784UegFdyi8c4pJcnoeMfYQszbKMA9+5o9pHiZC\n" +
            "Xxg7742xv+VDjm85EakKRg5OaesTE8wMahmXzFWGBgbn3qEpvyXit6AXdrHAxAkUgcAHOf5/igIr\n" +
            "HgOTjG22K0pDqiLlBgf/AKNkAewFZNzcZdsNnc42wPoB2pU7HnBx2dMeXw3fgA757UtGPHnjiY41\n" +
            "MFLDvk1xmldC6gkDZjp2oTOyyBwAjKditOQemm0dumj8Y+EMINhXIm8p3ww3Hv60WR/EtUBUAhTw\n" +
            "MZ3pINvVIkpveh+G4KY3Ox2Gdq0o7rUux3O+KybNfEc5AIA79q1EtvFGAo25ywXPpjNaI8mKJY5B\n" +
            "5pfFgBPK7GkHjBbPFNG2dDpifXq7E5HHr86UMmkkSDSR9c10+TJDR46PadN6raxfZqRGaKGTQ0Qi\n" +
            "QeZ2x94799t/Y+1Z3UPtNd21vatawyQDJZpJDq8ZhscnG4x/j0FLfZyBprn4mO6hhFuQ7F37Z7Ac\n" +
            "+n1x3rV+1csUlnFBIzz3H3iWGnQP/UcZ533A5rzJKpUaV0eF6jey9QvJbmc5kkYscdvYe1Sjm1hL\n" +
            "ZZW+SnFStCVE6KOc7nJz3oZOasxzV0TUN+TRC9kgi8QnU4UD170SJUBJcNpH50UokSDVqIzwozk/\n" +
            "PtyKvAY1YmVTncBRuaRyHSBBHkIXsNgBWpaQiPzo4CZ0hgMFs8/z3pbwWzoYFRjJAIOcgYHvv+u1\n" +
            "KX9wYtCLIcgbgNlR/N96m3lpF4JR+TJdzSB9Mp1YzjB2pIFpZFRdyTgdqo0pdskkmnoLAlSSSr55\n" +
            "PA9P5+9MkkhJ8jm7A3d1kiKElYkGAPr39T3+tJsxO+d67Irq3nBBNDO5pkQk7eyCRlJweeatDGZX\n" +
            "CrgepNDNM9O1G7VQ2M55O3FPZOtmrYwiPCRqzsTnAO5+lHkt1Eh1LLGSdvE/PJrjQmFP6wUak1Bp\n" +
            "MqAPUdzWNPcs8mkDyA8EnBNdF27GlpUbSzws2gyp5gPMF2HrnbI+maZlTS3iB7fQ41rqiBB+mD3B\n" +
            "H0rzspmtZFS4t2Rgv3ZUKnHY42/zTtreAxFWQBeQQ2Mbb8/L8qrpqiKcou7PRdHs4rYN1NlJjiOm\n" +
            "IcGSTnjPHr/yKeFzY9Yt7Vr74ma8i1B4YYsM3m4O2MADt6+tYEJneB1XPhqCcliVQnjGOGOKNYXy\n" +
            "2sMq3EMzB9L5STTtvgHbg6vXsKxTg07NiaZlXTsJ38O3ZU1HALZwPTON6lHuL557h5pUjdnJY5XG\n" +
            "5+VSqxbo5pCCICQS2Pluabts6ytrGx41SMpbT77ZxXPJDHtmRhsQOAd/TngGkZIpdLujAgnOkihe\n" +
            "RN2lrs0pbmO3UpK6u4IBbSTttnnBB/2+i56nCodYoxhhswGCD+3P5VmSRTh9LI7EcYyRVXimiVWk\n" +
            "idFbgspAP8xQwQy5JUP3PUH28MgKFxhTsPU/PFLywMIFmkkGX4XIzSy5Y4AzV4QzEsAG0jg+9Gq6\n" +
            "Bm32aHS7QTMjSDEbHDEc4/xWzfoYIBI5Tfyqxyw2+WaxumzmJlVlZdJyjBe+a3oAtykUcyHwpPK+\n" +
            "rcFh3A9f560kk7K8dNUuzyuh5mlcsPKCxJPNSzgE8+liQqjJIrZuLWO1llhYINJK6V4+dLRWdsLp\n" +
            "GNw8SZ8xRNQI+pFPerRPF3sUnsFUnRLnfuP2rluIrWRjJIRIF7LnHpj/AJrRuLNpZGFiweJCSH/v\n" +
            "Ye4J7DbbA259c6G1mmdXeNiBsBj0/n5UYyVCSi09I7cX/wAQyxqhVNWfM2pvx/2r0XSLJenWy3Eg\n" +
            "R3vUBjkkyoiHlbPodznPbSDzsMqRjJZtbQoiHw9Rx5tZAJ29Ns8eh+VPRX3/AMaIsrCJYv6SkjSo\n" +
            "GxH4/lRvQrjsU6l1IyzyEaWfGjxAOB3C+g5rMiKmZFb7mQCBV7splmUAH0GwH0paLQXzKXUYyNI5\n" +
            "NGLDJJKjcvJrb4eWOCNpVYgCRsrsN9wOT+x2rkUUHhhRdMF1awWBzk7fLsCfp8qQj6g1uw8NtRH9\n" +
            "xA323HHG527980e0vAtsSVyQcL5Qc+25/wAU01a0JxvF7GzbRZI8dWAP3sc/nmpSsk0ly/iSFQx7\n" +
            "ABfyFSpYy9mnJeh0EiMqJ0Rz2GTn5EbdvWoI4JmKLchVUc4Y59e3qcfzNcja1EZWRpRIwAwqK4x7\n" +
            "HO1Sb4dEWGETsAcsGON8fr2/5qKCy89rbwRoDL58caiNyRgbrxv8t6RuSL1iFAZQvhxDHYd8epO+\n" +
            "3+aLdSCV0CRMvJcr5cD1BA22zyDyK7pgt41nl8UKyltKrgpgkAZ2xnHseaopNLZ3ekYsiKgTQSNS\n" +
            "Anf1rgRtBYYwfeuTPrcsFAB4AGKtGniQ7nGltj7fzH4mqEAi+RNQO52O2ea0On3LGTwywQDzrk4x\n" +
            "jb/JrJ1lGKrxTdkW1sCRpwN64KdM9LcSW0lxFK91JHrXL+Gh227Y5z+uax5ri2tmRcljka1GCAO4\n" +
            "Bzg/hjNN3LQ/AwlismkedQeV32J7HYj2/XDe68S7eRwAHPlA/tHYfLFShE0TlW/Y/JceIWkhYiOQ\n" +
            "kadOnH/icc9t66zEpqZ3C4ARtzjHb+egqkUcR8ofBI22xv6GmI/iHiMADMmdhv5T6j+d6akuibdi\n" +
            "0dxGXS3aR2hY5LRvpOc49Dtz2BOd+2DzXcNvatEzB3DN5ETSNznH457bVkmVhIZWI1HO4UDNBd9X\n" +
            "bmqaaM+07LTTtM3cDgDPvmru8c+jzaZOONh/PlVYYHmJAAHfetCHp0L2+pZVMo2KE4z7ilclHsdR\n" +
            "cjMMbKxTIYjuu4NadpbmOHYGQg5OOxq1upgDYtlcd9QO31phz4x0whEc/wBijf6GhKTHjFLso5hU\n" +
            "gKgU43WQnb5YqVSePTIUfSHU+bQDUrkm1phbpjVkr5eVWTMK6t2Azvj9TQ3E+o/1BnGo4cHGflQI\n" +
            "2RUJbJ32UcE1Edpp8DRFrO+NgP8Aak8th/ok0ISNGKtqbcsRtjt/PlS3xksn9JS8juvhqM5wSccd\n" +
            "9iR//VHvrh7oSt4jNoXCo25Cj9hk5+ZpCCKWW8VI0PiZJCgHO2+Meu1NFWtiTdPR25VYmVAAVX0I\n" +
            "OfqM8/zijtCJrVvh0XQDqDFtxjOx9zjP8FKXEheZ3yctufnTvjvpWW2WGMuhDRAHAO4yAduDRada\n" +
            "Oi1exNoHjfScZKhhjuCM1eCQx7qfvdv53/erXOdKMSCcBOMEYFAVskhu/wCtFO0BqmOQ3AViJBq1\n" +
            "DzZ/Su2lot5faA3hKctsAfy2pWEF5AoxqJrcgjW0eGRRnOCT6+tUcbi67Ojb76QKO3FpNLbzsiKc\n" +
            "6HYFsen7V1JJgpVZQUYYO/b5Vq9XshNAZo8F1A4/uG2D+FY8EV1ExkX+nnK6mxjce9ZIckZxstKL\n" +
            "Tox7hTFIUOduM+lWt4Q7ajnFa9xBAgHiyLM5OSoOQvzPfnt71ZxawRRsF8V2GcZ2G/t8qrmtEvt7\n" +
            "AwzvGQysPLwDv+Fdn8UlHfcdiRRWulY6oreMKAFZSM/X1o0V7EyaLiEaQQfJsfzpW33iOl4sXe4S\n" +
            "dAJ9nACrpXbHvVXCRjzIWOMZzj5H+elHvZbdZNMcSFCAQDsRt3qtvOk0bW7Qpg7ht9m+n1H1o8ab\n" +
            "0lpnT/YPx9SKJE1Y2BJOQKlUllZXwAq+wUVKt+OkgZexvVFHAifDhrgE8bgDbH+a4kjCOWQhYpWO\n" +
            "iIr5dJzufXGNvrQzAVQFp00sdhk0W6E1ssUEOG0YL6MHJP69vwrGseijs1oETptjHazYe8nOY1ZV\n" +
            "OgkacA8f93tvvzmsLq9hJ0m5trkKys5MuHwRkHJXb2IHp784H8XKeoQsp82AEY4PhkEHOPkAMUz9\n" +
            "p7iR47RjIxJD6hyASFzg+h9O3bbAF0jO/wCzHuJkuZMyKyPvkgDc5zknvzz+1DjcqMDBP6VRVeUh\n" +
            "ERnIBOFGTgVxlfllYZ74pgIu7Eg53JNSGHXIFbUM9lG9Gt7G4mRphBK0SAsWCEqAACSTxwQfw9a9\n" +
            "DZXvQoOkyNJEjXzQlS582pjwQN8YH6UtlYQyYj07o87sXt4JJBkLrPAJIGM8DkVsnpVyvSpvHiYG\n" +
            "J2KlPMMqCCDjjjvitS8643URD/pvTpJXJWULM0cZZV3DAE6sZPPH50n0v7URSmETrDLIJ2mCwyeG\n" +
            "VLBhg+IAD948N6elL95dWUi4xQl0idvDeJ1JA3wfTv8A4rN6lbG3u3U5Kndd+1enTqPTrvqQeVZY\n" +
            "A0jpI8ieGVY6SoY7jgMME+/pQOuWcMsZFrOtyEUaWUhj8sjb/ms+4cmfhjtXGjyhHmGF7fjTY6t1\n" +
            "D4lLj4qQzJ91mI2/H60B4mUZcBfn9f2qoWMKGxqJJ8oOMVstGeh1Op395MUlnY6yzNghM7DPoP7c\n" +
            "7/PmmGsusGWDmZyvixF3ViAAueTsR5fesvWcbeXAxttR7C5FrOGeJJY22ZHXOR7e9BoNUaccfUmB\n" +
            "sWEhlaMnS1xHhQfKcZG2xAxnjPY1Yi9hAshISirq8NXjOEGQTq47/PbPphJbs2sjyNbWreJp8rQg\n" +
            "gY9B2z3otnBBHcyLHJFNpxkyWjuP7hjGNux/4NUqkL+xO8DR3TiUgA7hZHLEAgEfd9jUpe6eJ5dU\n" +
            "WgpgY0LpHA7fjUpcUdYeG28YsxmUBF1Y5wKDkzT7csdhXofslBDJadQJt4prhUVlWdSUA35PA/Gg\n" +
            "faRoLD7RtHZ2yp5FwAukBj3AqKbVlLV0ZVzreREjOtItlbGP53q9/wBJzZQSiZzqI1BsDtnavZ9V\n" +
            "soYluVbpcCW6NF4U8SrkAkasjOfYbV5/q9uoMcLy4SMsACNPHtUpTcZRihklNNmDHbwKuMEADc8m\n" +
            "qpFEt9BNKzCBZVZ874XI1Hb2pwPaBCCrn/yB3P0pu36ZP1C0eW0t38AEo+hwXO2T+RHbvjequTFc\n" +
            "Eeh6hcwL0m8aZoo08V4pQkgOo52Bxvk4UEZBwTuMZrJVJi0EdlaNL0vw5UleBFLvGZHwgZu2ADgE\n" +
            "H8qWuejdZIcS3lxoYP4sayKoAHmOrDY31Z98k0mvTOqW8IZI5DDZzMNJlG0gwAAvfDHOw3yaxcf0\n" +
            "mMe/IGn6NyDpMsnUIL1yqWQ6fGPFLDTkYOCO6nG/YjO9J9N1p0Pq/UpYFgtrgK0ShhjZ38ox8wOB\n" +
            "Q7rp3VLm3SBoVQPcOZI1ugAzFmULpPoQQMHHlO3enNPWIlsEewg8C3X/AKfxihXAV9D5H3SBqbUM\n" +
            "ZIztgYeP086py9ePCd+/IGn6HhBNNdxr1C3IhuL3KQTMrjSIGJ2BIxqGaxLiBLbptpepNHbwJoiW\n" +
            "RLd4JJs6T5/vZGlWOrzA+hok1l1mSwQFIkaKGVvFMw1kElizAeiswxjbUe5oi9G6jNPGXa5twjh0\n" +
            "iWcAx5GwRTjtnG2diOxpY/Syg7v/AJX++xlFsxYo3u/ipfiEkWEZ1qp/qNoZ8bhcDCNvjnHPNWjj\n" +
            "MbBsA+xG1ekuukPLaFmvWuYw/iHxpEZdQTOQQB/ZvjPG/vSMtkbc4kXB8w1HjZipx9Qa08Upy00P\n" +
            "GFbbMz4fbUxAzwM5NXHhRW7Mg1S7A6uFHsMfLvRblo1iysils404OT7+n50i3kdWEqMcA8Hv24rW\n" +
            "livbEk1YRp0mKrJ4mezFwQpzzxxjtnnf2p5fGEs8lxPLJHj7y9QXOMtyd9XfYDv71WO1b4NGUx+G\n" +
            "POcxLqHJ5xnG3faqTXMMkS2+uBYk8mrQ2o5zuCF7fjUc7fxFa8sSklVJDpXWCBgyNqIGPbb6duKl\n" +
            "SW1YSEQN8SnZ41OD+IqU3xBs3uh3BgteoFrWO5tZfDR18bQRuQN/TPyq/U5Jup3MlxJ0sLNKkSwM\n" +
            "bgApucbd9WD8sVj2tobiMW6sqSMplJdyq4HvwO/O1aE3T4Ydy6eApJLGRi2PciMhfXce3IOM7klo\n" +
            "d1dm3cX0krTpL0nwJLrSZWFxryE3xxgHH6153rYeS/cENrQ6Xyc4OT3rZtun/CFlWVfGVdUiNkeX\n" +
            "Gx+6Mb45HZhnakrm3bqFq9wSRBFuCoOCurTnIX5nO/f0IpIvLkteA2lExJolhIVjlj/b6D1rT6X1\n" +
            "49Ms5IEtY5GZ2dZGP3CdONsdioP4Va66LHHcyxo8vhxOuZDggJrZT822GB3wRyQKNP0ayj0okrNm\n" +
            "IM0guYtAJIXJBOQM5+uwNXe1TsCa9Bv9b6jBZrOkVokUmlVjXWMaT6Z+Q+QApKPrMzPbqLaILDKs\n" +
            "oCu2WKliBlie7H3o09rbmytI1nUoVLhz5AoLkFjkk7YA08knYnFG6d0y2uoInKmKOVtPiGUPhuAC\n" +
            "u2nLMMc8ds5qeMkvkmVXIk9Em611OWISII5sIF1KrjORpPYKd9wCDgnarTdR6k2oIlviRTraESjl\n" +
            "s5yBnYs2MbbnnajSWUSdPe68VfARx4IXkp4gVmK4zwQds/LgVW5tkjiypeJy5BWR1/pr5fv4+6cE\n" +
            "nAB/dFHkveh1ycfoFY9SuRcLJ8PCJGhMCq5cLGuSfMCCTv2B422o15d9QZpPES0ZXPLO5JUPrA2x\n" +
            "wduM4pUtBCiE3UUsjuVLiQgIMDGQV1Hk8elNXEMHw2tpxISjkGNsaSoyMhgDgk4z6kDk1SMdbsD5\n" +
            "I3ZWPq9+86uEsA6YIyZNsBhnHr5z+NIdSedtLXDBpCoDMCTqwAM7/L8c1pz9I6fZ3mppL4qqSM4E\n" +
            "TY8oyTnTgj3G2cetVv7aA9JSUOxR5CInbSMqE17nO2wPOD7UinKPImk6JynGS1o82rMCVRVOe5UH\n" +
            "9a6ZvCC6liZ12wY12+e29epm6DZWN5GGmk8F43bGxbYoBjY6iS2MAdxikZ/s7bpbyzSLcoiSOgka\n" +
            "RAjBXI78EgYBOBkjtVvuX4dEdIwPirmS51ow8U5PlUDt+1MymRlZYbVVkG8uoRkYGBt5RgZYcHfv\n" +
            "61p/6N074bqDpLMZYVZoA5GXAjVs4xvz9MgHBoNz0e3hCG5Y2zlVLJI4wDqcEayABsgx67kZAo5p\n" +
            "f6sFmJPM6zNpzETjKrgDIHsAKlWuIIomQKXw0Ub7juyAn02yTj2qVXT3R2wcrFpmJ9cfSusdQAPY\n" +
            "VKlKvAz8no+iovwHH3mGfzrzsxOs+9SpWbh/yyKS/FDEjtbRoITp1g6iOSOMfz1oMuVIZSQSM1Kl\n" +
            "aIisJbksfNuAM4pu7kYRxIpKpjVpB2B4z+QqVKSTf3Uh03g2LK7EgFiRmjSSNqaIHCA/dHGfWpUq\n" +
            "j20QbdF4AG1EjOBkVrWZ8aAxsPL7VKlF+S/0/Zg3cjvJliSRsPatHpszvaaWORGfL9alSofVL4B4\n" +
            "vyMe5Gi4kA4DEb0NWMZDrzmpUq66IPs5Kxdix5bc1yJiHA2Odt1BqVKPgHkZmbRKwUKBnH3RUqVK\n" +
            "CQWf/9k=\n";
    FrameLayout frameLayoutimg;
    int Matl=1;
    AddproductActivity addproductActivity;
    CategoryselectAdapter categoryselectAdapter;
    Product productadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddproductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listtype=findViewById(R.id.listtype);
        if(Info.darkmode){
            binding.getRoot().setBackgroundColor(Color.BLACK);
        }
        binding.imgtruyen.setImageBitmap(getImage(encodedImage));
        binding.textAddImage.setVisibility(View.GONE);
        frameLayoutimg=findViewById(R.id.layoutImage);
        frameLayoutimg.setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
         addproductActivity=this;
        getCategories();
        listtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Matl=Integer.valueOf(categorydatalist.get(i).getId()) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    binding.buttonadd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             if(isValidSignUpDetail()){
            getinsert();
             }
        }


    });
    }
    private void getinsert(){

        String name=binding.title.getText().toString();
        String tacgia=binding.tacgia.getText().toString();
        String img=encodedImage;
        String maTL=String.valueOf(Matl);
        String description=binding.description.getText().toString();
Product product=new Product(name,img, maTL,description, "nametype", 2,"ngayxuat",tacgia);
//        ApiService.apiService.inserttruyen(name,tacgia,img,maTL,description).enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//
//
//                Log.d("chayadd",name+" "+tacgia+" "+maTL+" "+description);
//                Product product=response.body();
//                Toast.makeText(getApplication(), product.getNgayxuat(),Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(AddproductActivity.this,AddChapActivity.class);
//                intent.putExtra("product",product);
//                //startActivity(intent);
//            }
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//                Toast.makeText(getApplication(), "chay that bai",Toast.LENGTH_LONG).show();
//            }
//        });

Call<Product> productCall= ApiClient.getUserServicd().sendPost(name,tacgia,img,maTL,description);
productCall.enqueue(new Callback<Product>() {
    @Override
    public void onResponse(Call<Product> call, Response<Product> response) {
        Toast.makeText(getApplication(), "thanh cong",Toast.LENGTH_LONG).show();
       // Log.d("chayadd",name+" "+tacgia+" "+maTL+" "+description);
                Product product=response.body();
                Toast.makeText(getApplication(), product.getNgayxuat(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AddproductActivity.this,AddChapActivity.class);
                intent.putExtra("product",product);
                startActivity(intent);
    }

    @Override
    public void onFailure(Call<Product> call, Throwable t) {
        Toast.makeText(getApplication(), "chay that bai",Toast.LENGTH_LONG).show();
    }
});
    }

    private final ActivityResultLauncher<Intent> pickImage=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if(result.getResultCode()==RESULT_OK){
                    if(result.getData()!=null){
                        Uri imageUri=result.getData().getData();
                        try {
                            InputStream inputStream=getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                            binding.imgtruyen.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage=encodeImage(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private String encodeImage(Bitmap bitmap){
        int previewWidth=150;
        int previewHeight=bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap=Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }
    private void getCategories(){

        ApiService.apiService.getcate().enqueue(new Callback<List<Categorydata>>() {
            @Override
            public void onResponse(Call<List<Categorydata>> call, Response<List<Categorydata>> response) {

                categorydatalist=response.body();
                categoryselectAdapter=new CategoryselectAdapter(AddproductActivity.this, R.layout.item_selected,categorydatalist);
                listtype.setAdapter(categoryselectAdapter);



            }
            @Override
            public void onFailure(Call<List<Categorydata>> call, Throwable t) {
                Toast.makeText(getApplication(), "chay that bai",Toast.LENGTH_LONG).show();
            }
        });}
    private Boolean isValidSignUpDetail(){


        if(encodedImage==null){
            showToast("Select profile image");
            return false;
        }else if(binding.title.getText().toString().trim().isEmpty()){
            showToast("enter title");
            return false;
        }

        else if(binding.description.getText().toString().trim().isEmpty()){
            showToast("enter description");
            return false;}

        else if(Matl==0){
            showToast("select type");
            return false;
        }
        else{
            return true;
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    private Bitmap getImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}