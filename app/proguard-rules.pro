# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/adt-bundle-mac-x86_64-20140702/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keep class com.purplepath.purplepath.CustomView.**{ *; }
-keep class com.purplepath.purplepath.assets.model.**{ *; }
-keep class com.purplepath.purplepath.model.**{ *; }
-keep class com.purplepath.purplepath.model.personnalmodel.**{ *; }
-keep class com.purplepath.purplepath.famlydetail.model.**{ *; }
-keep class com.purplepath.purplepath.incomedetails.fragment.model.**{ *; }
-keep class com.purplepath.purplepath.expenses.expensesmodel.**{ *; }
-keep class com.purplepath.purplepath.expensesRedesign.preretirementexpanse.expensesredesignmodel.**{ *; }
-keep class com.purplepath.purplepath.incomedetails.fragment.model.**{ *; }
-keep class com.purplepath.purplepath.goal.**{ *; }
-keep class com.purplepath.purplepath.liabilities.model.**{ *; }
-keep class com.purplepath.purplepath.insurance.model.**{ *; }
-keep class com.purplepath.purplepath.retirementbenefits.model.**{ *; }
-keep class com.purplepath.purplepath.expensesanalysis.model.**{ *; }
-keep class com.purplepath.purplepath.incomechartdetail.model.**{ *; }
-keep class com.purplepath.purplepath.goalanalysis.model.**{ *; }
-keep class com.purplepath.purplepath.expensesRedesign.**.{ *; }
-keep class com.purplepath.purplepath.networkanalysis.model.**{ *; }
-keep class com.purplepath.purplepath.assetsanalysis.model.**{ *; }
-keep class com.purplepath.purplepath.ScoreChartAnalysis.model.**{ *; }
-keep class com.purplepath.purplepath.cashmanaganalysis.model.**{ *; }
-keep class com.purplepath.purplepath.emergencyfundAnalysis.model.**{ *; }
-keep class com.purplepath.purplepath.goaltimeline.model.**{ *; }
-keep class com.purplepath.purplepath.cashflowmanagmentchart.model.**{ *; }
-keep class com.purplepath.purplepath.insuranceAnalysis.models.**{ *; }
-keep class com.purplepath.purplepath.marqueeModels.**{ *; }
-keep class com.purplepath.purplepath.settings.models.**{ *; }
-keep class com.purplepath.purplepath.taxanalysis.modes.**{ *; }
-keep class com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.**{ *; }
-keep class com.purplepath.purplepath.Notification.Models.**{ *; }
-keep class com.purplepath.purplepath.alertprompt.personalprompt.model.**{ *; }
-keep class com.purplepath.purplepath.settings.UpdateModels.**{ *; }
-keep class com.purplepath.purplepath.riskAssesment.models.**{ *; }
-keep class com.purplepath.purplepath.riskAssesment.scoreModels.**{ *; }
-keep class com.purplepath.purplepath.recommendation.model.**{ *; }
-keep class com.calculator.**{ *; }
-keep class com.purplepath.purplepath.desipro.models.**{ *; }
-keep class com.purplepath.purplepath.financialratio.model.**{ *; }
-keep class com.github.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Links.model.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Feedback.model.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Glossaries.Models.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Vendors.Models.**{ *; }
-keep class com.purplepath.purplepath.user.editProfile.model.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.FAQ.model.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.**{ *; }
-keep class com.purplepath.purplepath.cashflowmanagmentchart.model.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.models.GoalAffordabilityModels.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledgemodel.**{ *; }
-keep class com.purplepath.purplepath.recommendation.getsaveinvestmodel.**
-keep class com.purplepath.purplepath.user.MyAccount.getModels.GetProfiledata.**{ *; }
-keep class com.purplepath.purplepath.user.MyAccount.models.ProfileModels.**{ *; }
-keep class com.purplepath.purplepath.investmentPlan.models.InvestmentPlanModel.**{ *; }
-keep class com.purplepath.purplepath.recommendation.getnetworthmodel.Networthmodel.**{ *; }
-keep class com.purplepath.purplepath.schedule.models.UpdateScheduleModel.**{ *; }
-keep class com.purplepath.purplepath.schedule.models.ScheduleModel.**{ *; }
-keep class com.purplepath.purplepath.document.deleteModels.DeleteModels.**{ *; }
-keep class com.purplepath.purplepath.document.getfilemodels.GetDocumentModels.**{ *; }
-keep class com.purplepath.purplepath.document.model.DocumentModel.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.timeValueOfMoney.models.TimeValueOfMoneyModel.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Quiz.model.Quizmodel.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel.**{ *; }
-keep class com.purplepath.purplepath.AppManagement.Links.model.Linksmodel.**{ *; }


-keep class com.purplepath.purplepath.AppManagement.Survey.model.**{ *; }
-keep class com.purplepath.purplepath.Notification.Models.**{ *; }
-keep class com.purplepath.purplepath.ScoreChartAnalysis.**{ *; }
-keep class com.purplepath.purplepath.assets.model.**{ *; }
-keep class com.purplepath.purplepath.assetAnalysisNewPieChart.Model.**{ *; }

-keep class com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.loanComparison.models.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.loanEligibility.view.models.**{ *; }
-keep class com.purplepath.purplepath.desiproAllModules.timeValueOfMoney.models.**{ *; }
-keep class com.purplepath.purplepath.settings.models.SelectionsModel.**{ *; }


-keep class com.purplepath.purplepath.propertyinsurance.model.**{ *;}
-keep class com.purplepath.purplepath.chatprompt.insertmodel.**{ *;}

-keep class com.purplepath.purplepath.model.companyCategoryModel.**{ *;}
-keep class com.purplepath.purplepath.taxprompt.model.**{ *;}
-keep class com.purplepath.purplepath.taxprepaid.gettaxprepaidmodel.**{ *;}
-keep class com.purplepath.purplepath.taxprompt.gettaxpromptmodel.**{ *;}
-keep class com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.**{ *;}
-keep class com.purplepath.purplepath.lifeevent.model.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getdeclarationmodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getchecklist.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getchecklistdocument.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getsummary.**{ *;}
-keep class com.purplepath.purplepath.AppManagement.Payment.getcommonpaymentmodel.**{ *;}
-keep class com.purplepath.purplepath.AppManagement.Payment.gettaxpaymentmodel.**{ *;}
-keep class com.purplepath.purplepath.AppManagement.Payment.upgrademodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getconformationmodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.credencialmodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getUserStatusModel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getadharpanmodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.gettaxconformationcheckbox.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getformparse.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getdeletevalidationform.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getvalidateform.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getprevioustaxupdatemodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getsuccessparsemodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getvalidateparsetwentysix.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.addsessionmodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getplanamtmodel.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getvalidateformmultiple.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.getdialogforminstruction.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.freeuserconfirmation.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.resettaxfiling.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.model.**{ *;}
-keep class com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model.**{ *;}

-keep class com.purplepath.purplepath.AppManagement.Payment.promocodemodel.**{ *;}
-keep class com.purplepath.purplepath.AppManagement.Payment.getdiscountmodel.**{ *;}



-dontwarn com.medialablk.easygifview.EasyGifView.**
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}
-keep class com.github.mikephil.charting.** { *; }
-dontwarn io.realm.**
-dontwarn okio.**
-dontwarn rx.**
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }
-keep class com.facebook.** { *;}
-ignorewarnings
-keep class * {
    public private *;
}