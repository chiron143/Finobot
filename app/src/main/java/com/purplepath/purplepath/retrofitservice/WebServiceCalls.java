package com.purplepath.purplepath.retrofitservice;

import com.purplepath.purplepath.AppManagement.FAQ.model.Faqmodel;
import com.purplepath.purplepath.AppManagement.Feedback.model.Feedbackmodel;
import com.purplepath.purplepath.AppManagement.Glossaries.Models.GlossariesModel;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledgemodel;
import com.purplepath.purplepath.AppManagement.Links.model.Linksmodel;
import com.purplepath.purplepath.AppManagement.Payment.getcommonpaymentmodel.CommonPaymentModel;
import com.purplepath.purplepath.AppManagement.Payment.getdiscountmodel.DiscountModel;
import com.purplepath.purplepath.AppManagement.Payment.gettaxpaymentmodel.TaxPaymentModels;
import com.purplepath.purplepath.AppManagement.Payment.promocodemodel.PromoCodeModel;
import com.purplepath.purplepath.AppManagement.Payment.upgrademodel.UpgradePaymentModel;
import com.purplepath.purplepath.AppManagement.Quiz.model.Quizmodel;
import com.purplepath.purplepath.AppManagement.Quiz.topicModels.GetQuizTopics;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveymodel;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveytomodel;
import com.purplepath.purplepath.AppManagement.Vendors.Models.VendorDetailsModel;
import com.purplepath.purplepath.Notification.Models.AlertsModel;
import com.purplepath.purplepath.Notification.Models.NotificationsModel;
import com.purplepath.purplepath.Notification.Models.PromptsModel;
import com.purplepath.purplepath.ScoreChartAnalysis.model.ScoreAnalysisModel;
import com.purplepath.purplepath.alertprompt.personalprompt.model.PersonalPromptModel;
import com.purplepath.purplepath.assetAnalysisNewPieChart.Model.AssetAnalysisNewModel;
import com.purplepath.purplepath.assets.model.AddAssetModel;
import com.purplepath.purplepath.assets.model.AssetCategoriesModel;
import com.purplepath.purplepath.assets.model.DeleteAssetModel;
import com.purplepath.purplepath.assets.model.GetAssetModel;
import com.purplepath.purplepath.assetsanalysis.model.AssestAnalysisModel;
import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowAssetflowModel;
import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowinoutflowModel;
import com.purplepath.purplepath.cashflowmanagmentchart.model.IncomeExpenseCashFlowModel;
import com.purplepath.purplepath.cashmanaganalysis.model.CashManagentAnalyisModel;
import com.purplepath.purplepath.chatprompt.insertmodel.InsertModel;
import com.purplepath.purplepath.chatprompt.model.GetPromptModel;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CalculateLeasePaymentModel;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CarVsLeaseModel;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel;
import com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.models.GoalAffordabilityModels;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.LoanRecomendationModel;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.OutstandingBalModel;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.EmiModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel;
import com.purplepath.purplepath.desiproAllModules.loanEligibility.view.models.LoanEligibilityModel;
import com.purplepath.purplepath.desiproAllModules.timeValueOfMoney.models.TimeValueOfMoneyModel;
import com.purplepath.purplepath.document.deleteModels.DeleteModels;
import com.purplepath.purplepath.document.getfilemodels.GetDocumentModels;
import com.purplepath.purplepath.document.model.DocumentModel;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.ExpensesSelectionAddModel;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
import com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel.ExpensesUpdateModel;
import com.purplepath.purplepath.expensesanalysis.model.ExpensesAnalysisModel;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.famlydetail.model.DeleteFamilyDetailModel;
import com.purplepath.purplepath.financialratio.model.FinanceRatioModel;
import com.purplepath.purplepath.goal.DeleteGoalsModel;
import com.purplepath.purplepath.goal.GetGoalsListModel;
import com.purplepath.purplepath.goal.GoalCategoriesModel;
import com.purplepath.purplepath.goal.GoalFamilyDetailsModel;
import com.purplepath.purplepath.goalanalysis.model.GoalAnalysisModel;
import com.purplepath.purplepath.goaltimeline.getGoalPlanModel.GoalPlanModel;
import com.purplepath.purplepath.goaltimeline.model.GoalTimeLineModel;
import com.purplepath.purplepath.goaltimeline.retirement_models.RetirementModel;
import com.purplepath.purplepath.incomechartdetail.model.IncomeAnalysisModel;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeInputModel;
import com.purplepath.purplepath.insurance.model.AddInsuranceModel;
import com.purplepath.purplepath.insurance.model.DeleteInsuranceModel;
import com.purplepath.purplepath.insurance.model.GetInsuranceModel;
import com.purplepath.purplepath.insurance.model.InsuranceCatogoryModel;
import com.purplepath.purplepath.insuranceAnalysis.models.InsuranceChartModel;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlanModel;
import com.purplepath.purplepath.liabilities.model.AddLiabilityModel;
import com.purplepath.purplepath.liabilities.model.LiabCategoryModel;
import com.purplepath.purplepath.liabilitiesanaysis.model.Liab_Anaysis_Model;
import com.purplepath.purplepath.lifeevent.model.LifeEventModel;
import com.purplepath.purplepath.marqueeModels.GetAllMarketData;
import com.purplepath.purplepath.model.AccessToken;
import com.purplepath.purplepath.model.AddAndUpdateGoalModel;
import com.purplepath.purplepath.model.AddDeviceIdModel;
import com.purplepath.purplepath.model.AddPersonalDetailsModel;
import com.purplepath.purplepath.model.CardPermission;
import com.purplepath.purplepath.model.CardResponse;
import com.purplepath.purplepath.model.ForgotPasswordModel;
import com.purplepath.purplepath.model.GetcashflowGoalInvestmentModel;
import com.purplepath.purplepath.model.LoginModel;
import com.purplepath.purplepath.model.SignUpModel;
import com.purplepath.purplepath.model.VerificationModel;
import com.purplepath.purplepath.model.companyCategoryModel.CompanyCategorieModel;
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;
import com.purplepath.purplepath.model.personnalmodel.GetPersonnalDetailsModel;
import com.purplepath.purplepath.model.riskmodel.RiskDailyUpdateModel;
import com.purplepath.purplepath.model.versionUpdatemodel.VersionModel;
import com.purplepath.purplepath.networthanalysis.model.NetworkAnalysisModel;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.recommendation.getcashmodel.Cashmodel;
import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;
import com.purplepath.purplepath.recommendation.getnetworthmodel.Networthmodel;
import com.purplepath.purplepath.recommendation.getsaveinvestmodel.Saveinvestmodel;
import com.purplepath.purplepath.recommendation.model.RecommendData;
import com.purplepath.purplepath.retirementbenefits.model.RetirementBenifitModel;
import com.purplepath.purplepath.riskAssesment.models.RiskProfileModel;
import com.purplepath.purplepath.riskAssesment.scoreModels.RiskProfileByScoreModel;
import com.purplepath.purplepath.schedule.models.ScheduleModel;
import com.purplepath.purplepath.schedule.models.UpdateScheduleModel;
import com.purplepath.purplepath.settings.UpdateModels.AsssumptionsUpdateModel;
import com.purplepath.purplepath.settings.UpdateModels.SelectionsUpdateModel;
import com.purplepath.purplepath.settings.models.AssumptionsModel;
import com.purplepath.purplepath.settings.models.SelectionsModel;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxanalysis.modes.GetTaxPlanModels;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model.TaxFilingModel;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.model.TaxPromptModels;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.credencialmodel.CredencialModel;
import com.purplepath.purplepath.taxfiling.freeuserconfirmation.TaxFreeUserPdfModel;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxfiling.getadharpanmodel.AdharModel;
import com.purplepath.purplepath.taxfiling.getchecklist.ChecklistModel;
import com.purplepath.purplepath.taxfiling.getchecklistdocument.GetChecklistModel;
import com.purplepath.purplepath.taxfiling.getconformationmodel.ConformationModel;
import com.purplepath.purplepath.taxfiling.getdeclarationmodel.DeclarationModel;
import com.purplepath.purplepath.taxfiling.getdeletevalidationform.DeleteValidationFormModel;
import com.purplepath.purplepath.taxfiling.getdialogforminstruction.TaxFileFormInstructionModel;
import com.purplepath.purplepath.taxfiling.getplanamtmodel.PlanAmountModel;
import com.purplepath.purplepath.taxfiling.getprevioustaxupdatemodel.TaxFilePreviousUpdateModel;
import com.purplepath.purplepath.taxfiling.getsuccessparsemodel.ParseFormSuccessModel;
import com.purplepath.purplepath.taxfiling.getsummary.TaxFileSummaryModel;
import com.purplepath.purplepath.taxfiling.gettaxconformationcheckbox.TaxConfirmationModel;
import com.purplepath.purplepath.taxfiling.getvalidateform.TaxValidationModel;
import com.purplepath.purplepath.taxfiling.getvalidateformmultiple.TaxValidationModelMultiple;
import com.purplepath.purplepath.taxfiling.getvalidateparsetwentysix.TwentySixParseValidateModel;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;
import com.purplepath.purplepath.taxprepaid.gettaxprepaidmodel.TaxPrepaidmodel;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;
import com.purplepath.purplepath.taxprompt.model.DeleteTaxStatemetModel;
import com.purplepath.purplepath.taxprompt.model.TaxPromptAnserModel;
import com.purplepath.purplepath.taxprompt.model.TaxPromptModel;
import com.purplepath.purplepath.user.MyAccount.getModels.GetProfiledata;
import com.purplepath.purplepath.user.MyAccount.models.ProfileModels;
import com.purplepath.purplepath.user.editProfile.model.ChangepwdModel;

import retrofit.mime.TypedFile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static android.R.attr.password;

public interface WebServiceCalls {


    @FormUrlEncoded
    @POST("users/user_sign_up_13")
    Call<SignUpModel> callRegisterService(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("country_code") String country_code,
            @Field("user_cat_id") String user_cat_id,
            @Field("otp_type") String otp_type);


    @FormUrlEncoded
    @POST("push/send_verification_code_email_13")
    Call<VerificationModel> GetVerificationCode(
            @Field("email") String email,
            @Field("user_cat_id") String user_cat_id,
            @Field("mobile") String mobile,
            @Field("country_code") String country_code);

    @FormUrlEncoded
    @POST("users/forgot_password")
    Call<ForgotPasswordModel> ForgotPasswordService(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("users/change_password")
    Call<ForgotPasswordModel> changePasswordService(
            @Field("email") String email, @Field("new_password") String password);

    @FormUrlEncoded
    @POST("users/change_forgot_password")
    Call<ForgotPasswordModel> changeForgotPasswordService(
            @Field("email") String email, @Field("new_password") String password);

    @FormUrlEncoded
    @POST("users/login_12")
    Call<LoginModel> CallLoginService(
            @Field("email") String email,
            @Field("password") String password,
            @Field("login_flag") String loginflag);

    @FormUrlEncoded
    @POST("oauth/login_12")
    Call<LoginModel> CallLoginoauthService(
            @Field("email") String email,
            @Field("password") String password,
            @Field("login_flag") String loginflag);

    @FormUrlEncoded
    @POST("push/add_deviceid_android")
    Call<AddDeviceIdModel> AddDeviceIDService(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("imei_no") String imei_no);


    @FormUrlEncoded
    @POST("users/login_12")
    Call<LoginModel> CallLoginSocialService(
            @Field("social_media_id") String social_media_id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("login_flag") String loginflag,
            @Field("is_social_media") String is_social_media);

    @FormUrlEncoded
    @POST("users/user_sign_up_12")
    Call<SignUpModel> callRegisterSocialMediaService(
            @Field("social_media_id") String social_media_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("is_social_media") String flag,
            @Field("phone") String phone,
            @Field("country_code") String country_code,
            @Field("user_cat_id") String user_cat_id);


    @FormUrlEncoded
    @POST("users/get_personal_details_by_user")
    Call<GetPersonnalDetailsModel> callGetPersonnalDetailService(
            @Field("user_id") String user_id);


//    @FormUrlEncoded
//    @POST("users/update_personal_details")
//    Call<PersonalDetailsModel> callUpdatePersonalDetailService(
//            @Field("user_id") String user_id,
//            @Field("pid") String pid,
//            @Field("name") String name,
//            @Field("dob") String dob,
//            @Field("age") String age,
//    @Field("country_code") String country_code,
//    @Field("mob_no") String mob_no,
//    @Field("email_id") String email_id,
//    @Field("gender") String gender,
//    @Field("martial_status") String martial_status,
//    @Field("education") String education,
//    @Field("occupation") String occupation,
//    @Field("current_designation") String current_designation,
//    @Field("current_org") String current_org,
//    @Field("address_home") String address_home,
//    @Field("address_work") String address_work,
//    @Field("no_of_ava_years") String no_of_ava_years,
//    @Field("no_of_work_years") String no_of_work_years,
//    @Field("life_expectancy_age") String life_expectancy_age,
//    @Field("planned_retirement_age") String planned_retirement_age,
//    @Field("marriage_date") String marriage_date,
//    @Field("married_since") String married_since,
//            @Field("ah_city") String ah_city,
//            @Field("ah_state") String ah_state,
//            @Field("ah_country") String ah_country,
//            @Field("ah_zipcode") String ah_zipcode,
//            @Field("aw_city") String aw_city,
//            @Field("aw_state") String aw_state,
//            @Field("aw_country") String aw_country,
//            @Field("aw_zipcode") String aw_zipcode);


    @FormUrlEncoded
    @POST("users/add_personal_details")
    Call<AddPersonalDetailsModel> addPersonalDetailService(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("middle_name") String middle_name,
            @Field("last_name") String last_name,
            @Field("dob") String dob,
            @Field("age") String age,
            @Field("country_code") String country_code,
            @Field("mob_no") String mob_no,
            @Field("email_id") String email_id,
            @Field("gender") String gender,
            @Field("martial_status") String martial_status,
            @Field("education") String education,
            @Field("occupation") String occupation,
            @Field("current_designation") String current_designation,
            @Field("current_org") String current_org,
            @Field("address_home") String address_home,
            @Field("address_work") String address_work,
            @Field("no_of_ava_years") String no_of_ava_years,
            @Field("no_of_work_years") String no_of_work_years,
            @Field("life_expectancy_age") String life_expectancy_age,
            @Field("planned_retirement_age") String planned_retirement_age,
            @Field("marriage_date") String marriage_date,
            @Field("married_since") String married_since,
            @Field("ah_city") String ah_city,
            @Field("ah_state") String ah_state,
            @Field("ah_country") String ah_country,
            @Field("ah_zipcode") String ah_zipcode,
            @Field("aw_city") String aw_city,
            @Field("aw_state") String aw_state,
            @Field("aw_country") String aw_country,
            @Field("aw_zipcode") String aw_zipcode,
            @Field("alias_name") String mAlias_name,
            @Field("residential_status") String residential_status,
            @Field("citizenship_status") String citizenship_status,
            @Field("government_sector") String government_sector,

            @Field("ah_res_no") String ah_res_no,
            @Field("ah_res_name") String ah_res_name,
            @Field("ah_road_street") String ah_road_street,
            @Field("ah_locality_area") String ah_locality_area,
            @Field("aw_res_no") String aw_res_no,
            @Field("aw_res_name") String aw_res_name,
            @Field("aw_road_street") String aw_road_street,
            @Field("aw_locality_area") String aw_locality_area);
//    @FormUrlEncoded
//    @POST("users/add_family_details")
//    Call<AddFamilyDetailModel> addFamilyService(@Field("user_id") String user_id, @Field("family_details") String family_details);

    @FormUrlEncoded
    @POST("users/add_family_detail")
    Call<AddFamilyDetailModel> addFamilyService(@Field("user_id") String user_id,
                                                @Field("name") String name,
                                                @Field("relationship") String relationship,
                                                @Field("dob") String dob,
                                                @Field("age") String age,
                                                @Field("gender") String gender,
                                                @Field("marital_status") String marital_status,
                                                @Field("occupation") String occupation,
                                                @Field("education") String education,
                                                @Field("current_designation") String current_designation,
                                                @Field("current_org") String current_org,
                                                @Field("no_of_work_years") String no_of_work_years,
                                                @Field("dependant_life_expectancy_age") String dependant_life_expectancy_age,
                                                @Field("dependant_retirement_age") String dependant_retirement_age);

    @FormUrlEncoded
    @POST("users/update_family_detail")
    Call<AddFamilyDetailModel> addUpdateFamilyService(@Field("user_id") String user_id,
                                                      @Field("name") String name,
                                                      @Field("relationship") String relationship,
                                                      @Field("dob") String dob,
                                                      @Field("age") String age,
                                                      @Field("gender") String gender,
                                                      @Field("marital_status") String marital_status,
                                                      @Field("occupation") String occupation,
                                                      @Field("education") String education,
                                                      @Field("current_designation") String current_designation,
                                                      @Field("current_org") String current_org,
                                                      @Field("no_of_work_years") String no_of_work_years,
                                                      @Field("dependant_life_expectancy_age") String dependant_life_expectancy_age,
                                                      @Field("dependant_retirement_age") String dependant_retirement_age,
                                                      @Field("fid") String fid);


    @FormUrlEncoded
    @POST("goals/add_general_goal")
    Call<AddAndUpdateGoalModel> callAddGoalService(
            @Field("user_id") String user_id,
            @Field("goal_name") String goal_name,
            @Field("goal_years") String goal_years,
            @Field("goal_frequency") String goal_frequency,
            @Field("goal_recurrence") String goal_recurrence,
            @Field("goal_interval") String goal_interval,
            @Field("recur_months") String recur_months,
            @Field("recur_years") String recur_years,
            @Field("cost_of_goal") String cost_of_goal,
            @Field("goal_imp") String goal_imp,
            @Field("goal_priority") String goal_priority,
            @Field("goal_flexibility") String goal_flexibility,
            @Field("goal_duration") String goal_duration,
            @Field("expected_increment") String expected_increment,
            @Field("notes") String notes,
            @Field("belongs_to_id") String belongs_to_id,
            @Field("goal_cat_lev1_id") String goal_cat_lev1_id,
            @Field("goal_cat_lev2_id") String goal_cat_lev2_id,
            @Field("goal_cat_lev3_id") String goal_cat_lev3_id,
            @Field("other_category") String other_category

    );


    @FormUrlEncoded
    @POST("goals/update_general_goal")
    Call<AddAndUpdateGoalModel> callUpdateGoalService(
            @Field("user_id") String user_id,
            @Field("goal_id") String goal_id,
            @Field("goal_name") String goal_name,
            @Field("goal_years") String goal_years,
            @Field("goal_frequency") String goal_frequency,
            @Field("goal_recurrence") String goal_recurrence,
            @Field("goal_interval") String goal_interval,
            @Field("recur_months") String recur_months,
            @Field("recur_years") String recur_years,
            @Field("cost_of_goal") String cost_of_goal,
            @Field("goal_imp") String goal_imp,
            @Field("goal_priority") String goal_priority,
            @Field("goal_flexibility") String goal_flexibility,
            @Field("goal_duration") String goal_duration,
            @Field("expected_increment") String expected_increment,
            @Field("notes") String notes,
            @Field("belongs_to_id") String belongs_to_id,
            @Field("goal_cat_lev1_id") String goal_cat_lev1_id,
            @Field("goal_cat_lev2_id") String goal_cat_lev2_id,
            @Field("goal_cat_lev3_id") String goal_cat_lev3_id,
            @Field("other_category") String other_category);


    @FormUrlEncoded
    @POST("goals/get_general_goals_by_user")
    Call<GetGoalsListModel> callGetGoalsListService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("oauth/get_general_goals_by_user")
    Call<GetGoalsListModel> callGetGoalsListServiceOAuth(
            @Header("Authorization") String Authorization,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("goals/delete_general_goal")
    Call<DeleteGoalsModel> callDeleteGoalsListService(
            @Field("goal_id") String goal_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("assets/delete_user_asset")
    Call<DeleteAssetModel> callDeleteAssetsListService(
            @Field("asst_id") String asst_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("users/get_family_details")
    Call<GoalFamilyDetailsModel> callFamilyDetailsListService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("users/get_family_details")
    Call<AddFamilyDetailModel> callFamilyDetailsService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("users/delete_family_detail")
    Call<DeleteFamilyDetailModel> callDeleteFamilyDetailsService(@Field("user_id") String user_id, @Field("fid") String fid);

    @POST("categories/get_goal_categories")
    Call<GoalCategoriesModel> callGoalCategoriesService();

    @POST("categories/get_income_categories")
    Call<IncomeCategoryModel> callIncomeCategories();

    @FormUrlEncoded
    @POST("income/add_user_income")
    Call<IncomeInputModel> callAddIncomeService(@Field("user_id") String user_id,
                                                @Field("family_id") String family_id,
                                                @Field("level_1_ids") String level_1_ids,
                                                @Field("level_2_ids") String level_2_ids,
                                                @Field("level_3_ids") String level_3_ids,
                                                @Field("over_all_total") String over_all_total,
//                                                @Field("si_total") String si_total,
//                                                @Field("ip_total") String ip_total,
//                                                @Field("ib_total") String ib_total,
//                                                @Field("cg_total") String cg_total,
//                                                @Field("ifs_total") String ifs_total,
//                                                @Field("notes") String notes,
                                                @Field("income_details") String income_details,
                                                @Field("is_own_user") String isOwn);

    @FormUrlEncoded
    @POST("income/add_user_income")
    Call<IncomeInputModel> callAddIncomeSelectService(@Field("user_id") String user_id,
                                                      @Field("level_1_ids") String level_1_ids,
                                                      @Field("level_2_ids") String level_2_ids,
                                                      @Field("level_3_ids") String level_3_ids,
                                                      @Field("family_id") String family_id,
                                                      @Field("is_own_user") String is_own_user);

    @FormUrlEncoded
    @POST("income/add_user_postret_income")
    Call<IncomeInputModel> callAddIncomepostretService(@Field("user_id") String user_id,
                                                       @Field("family_id") String family_id,
                                                       @Field("level_1_ids") String level_1_ids,
                                                       @Field("level_2_ids") String level_2_ids,
                                                       @Field("level_3_ids") String level_3_ids,
                                                       @Field("over_all_total") String over_all_total,
//                                                @Field("si_total") String si_total,
//                                                @Field("ip_total") String ip_total,
//                                                @Field("ib_total") String ib_total,
//                                                @Field("cg_total") String cg_total,
//                                                @Field("ifs_total") String ifs_total,
//                                                @Field("notes") String notes,
                                                       @Field("income_details") String income_details,
                                                       @Field("is_own_user") String isOwn);

    @FormUrlEncoded
    @POST("income/add_user_postret_income")
    Call<IncomeInputModel> callAddIncomepostretSelectService(@Field("user_id") String user_id,
                                                             @Field("level_1_ids") String level_1_ids,
                                                             @Field("level_2_ids") String level_2_ids,
                                                             @Field("level_3_ids") String level_3_ids,
                                                             @Field("family_id") String family_id,
                                                             @Field("is_own_user") String is_own_user);
//    @FormUrlEncoded
//    @POST("income/update_user_income")
//    Call<IncomeInputModel> callUpdateIncomeService(@Field("user_id") String user_id,
//                                                @Field("family_id") String family_id,
//                                                @Field("over_all_total")String over_all_total,
////                                                @Field("si_total")String si_total,
////                                                @Field("ip_total")String ip_total,
////                                                @Field("ib_total")String ib_total,
////                                                @Field("cg_total")String cg_total,
////                                                @Field("ifs_total")String ifs_total,
////                                                @Field("notes")String notes,
//                                                @Field("income_details")String income_details,
//                                                   @Field("is_own_user") String isOwn);


//    @FormUrlEncoded
//    @POST("expense/add_user_expense")
//    Call<AddExpensesDetailModel> callAddExpensesService(@Field("user_id") String user_id,
//                                                        @Field("overall_expense") String overall_expense,
//                                                        @Field("is_own_user") String is_own_user,
//                                                        @Field("exp_details")  String exp_details,
//                                                        @Field("family_id") String family_id);
//
//
//
//    @FormUrlEncoded
//    @POST("expense/add_user_expense")
//    Call<AddExpensesDetailModel> callUpdateExpensesService(@Field("user_id") String user_id,
//                                                           @Field("overall_expense") String overall_expense,
//                                                        @Field("family_id") String family_id,
////                                                        @Field("exp_id") String exp_id,
//                                                           @Field("is_own_user") String is_own_user,
//                                                        @Field("exp_details")  String exp_details);


    @POST("categories/get_asset_categories")
    Call<AssetCategoriesModel> callAssetsCategoriesService();


    @FormUrlEncoded
    @POST("assets/add_user_asset")
    Call<AddAssetModel> calladdAssetService(@Field("user_id") String user_id,
                                            @Field("family_id") String family_id,
                                            @Field("type") String type,
                                            @Field("asset_name") String asset_name,
                                            @Field("cat_lev1_id") String cat_lev1_id,
                                            @Field("cat_lev2_id") String cat_lev2_id,
                                            @Field("cat_lev3_id") String cat_lev3_id,
                                            @Field("other_cat") String other_cat,
                                            @Field("objective") String objective,
                                            @Field("current_value") String current_value,
                                            @Field("annual_contr") String annual_contr,
                                            @Field("freq_of_contr") String freq_of_contr,
                                            @Field("years_of_contr") String years_of_contr,
                                            @Field("years_to_maturity") String years_to_maturity,
                                            @Field("alloc_flag") String alloc_flag,
                                            @Field("alloc_to_goal") String alloc_to_goal,
                                            @Field("purchase_date") String purchase_date,
                                            @Field("purchase_val") String purchase_val,
                                            @Field("notes") String notes,
                                            @Field("is_own_house") String is_own_house);

    @POST("categories/get_liab_categories")
    Call<LiabCategoryModel> callLiablityCategory();


    @FormUrlEncoded
    @POST("assets/update_user_asset")
    Call<AddAssetModel> callupdateAssetService(@Field("user_id") String user_id,
                                               @Field("family_id") String family_id,
                                               @Field("type") String type,
                                               @Field("asst_id") String asst_id,
                                               @Field("asset_name") String asset_name,
                                               @Field("cat_lev1_id") String cat_lev1_id,
                                               @Field("cat_lev2_id") String cat_lev2_id,
                                               @Field("cat_lev3_id") String cat_lev3_id,
                                               @Field("other_cat") String other_cat,
                                               @Field("objective") String objective,
                                               @Field("current_value") String current_value,
                                               @Field("annual_contr") String annual_contr,
                                               @Field("freq_of_contr") String freq_of_contr,
                                               @Field("years_of_contr") String years_of_contr,
                                               @Field("years_to_maturity") String years_to_maturity,
                                               @Field("alloc_flag") String alloc_flag,
                                               @Field("alloc_to_goal") String alloc_to_goal,
                                               @Field("purchase_date") String purchase_date,
                                               @Field("purchase_val") String purchase_val,
                                               @Field("notes") String notes,
                                               @Field("is_own_house") String is_own_house);


    @FormUrlEncoded
    @POST("assets/get_assets_by_user")
    Call<GetAssetModel> callGetAssetService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("liabilities/add_user_liab")
    Call<AddLiabilityModel> calladdliabilityService(@Field("user_id") String user_id,
                                                    @Field("type") String type,
                                                    @Field("liab_name") String liab_name,
                                                    @Field("cat_lev1_id") String cat_lev1_id,
                                                    @Field("cat_lev2_id") String cat_lev2_id,
                                                    @Field("cat_lev3_id") String cat_lev3_id,
                                                    @Field("lender") String lenderName,
                                                    @Field("loan_amt") String loan_amt,
                                                    @Field("outst_bal") String outst_bal,
                                                    @Field("current_emi") String current_emi,
                                                    @Field("interest_rate") String interest_rate,
                                                    @Field("total_tenure") String total_tenure,
                                                    @Field("balance_tenure") String balance_tenure,
                                                    @Field("start_year") String start_year,
                                                    @Field("end_year") String end_year,
                                                    @Field("whose_name") String whose_name,
                                                    @Field("insured") String insured,
                                                    @Field("linked_asset") String linkedasset,
                                                    @Field("last_paid_date") String lastpaiddate,
                                                    @Field("next_due_date") String nextduedate,
                                                    @Field("loan_freq") String loanfreq);

    @FormUrlEncoded
    @POST("liabilities/update_user_liab")
    Call<AddLiabilityModel> callUpdateliabilityService(@Field("user_id") String user_id,
                                                       @Field("liab_id") String liab_id,
                                                       @Field("type") String type,
                                                       @Field("liab_name") String liab_name,
                                                       @Field("cat_lev1_id") String cat_lev1_id,
                                                       @Field("cat_lev2_id") String cat_lev2_id,
                                                       @Field("cat_lev3_id") String cat_lev3_id,
                                                       @Field("lender") String lenderName,
                                                       @Field("loan_amt") String loan_amt,
                                                       @Field("outst_bal") String outst_bal,
                                                       @Field("current_emi") String current_emi,
                                                       @Field("interest_rate") String interest_rate,
                                                       @Field("total_tenure") String total_tenure,
                                                       @Field("balance_tenure") String balance_tenure,
                                                       @Field("start_year") String start_year,
                                                       @Field("end_year") String end_year,
                                                       @Field("whose_name") String whose_name,
                                                       @Field("insured") String insured,
                                                       @Field("linked_asset") String linkedasset,
                                                       @Field("last_paid_date") String lastpaiddate,
                                                       @Field("next_due_date") String nextduedate,
                                                       @Field("loan_freq") String loanfreq);

    @FormUrlEncoded
    @POST("liabilities/get_liab_analysis_by_user")
    Call<Liab_Anaysis_Model> callLiabilityanaysisService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("liabilities/get_liab_by_user")
    Call<AddLiabilityModel> callGetLiablityByUser(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("insurance/get_insur_by_user")
    Call<GetInsuranceModel> callGetInsuranceService(@Field("user_id") String user_id);


    @POST("categories/get_ins_categories")
    Call<InsuranceCatogoryModel> callCatagoryService();


    @FormUrlEncoded
    @POST("insurance/delete_user_insur")
    Call<DeleteInsuranceModel> callDeleteInsuranceListService(
            @Field("ins_id") String ins_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("insurance/add_user_insur")
    Call<AddInsuranceModel> calladdInsuranceService(@Field("user_id") String user_id,
                                                    @Field("family_id") String family_id,
                                                    @Field("ins_type") String type,
                                                    @Field("policy_name") String asset_name,
                                                    @Field("coverage") String cat_lev1_id,
                                                    @Field("annual_prem") String cat_lev2_id,
                                                    @Field("exp_incr") String cat_lev3_id,
                                                    @Field("policy_issue_date") String other_cat,
                                                    @Field("last_prem_date") String objective,
                                                    @Field("next_prem_date") String current_value,
                                                    @Field("prem_due_date") String annual_contr,
                                                    @Field("policy_end_date") String freq_of_contr,
                                                    @Field("maturity_date") String years_of_contr,
                                                    @Field("term_years") String years_to_maturity,
                                                    @Field("notes") String alloc_to_goal,
                                                    @Field("last_paid_date") String last_paid_date,
                                                    @Field("ins_freq") String ins_freq,
                                                    @Field("ins_sub_type") String ins_sub_type,
                                                    @Field("ins_prod_type") String ins_prod_type,
                                                    @Field("ins_sub_det") String ins_sub_det,
                                                    @Field("plan_type") String plan_type,
                                                    @Field("motor_type") String motor_type);


    @FormUrlEncoded
    @POST("liabilities/delete_user_liab")
    Call<DeleteGoalsModel> callDeleteLibListService(
            @Field("liab_id") String liab_id,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("income/add_ret_benefit")
    Call<RetirementBenifitModel> callAddRetirementBenifitService(
            @Field("user_id") String user_id,
            @Field("gratuity") String gratuity,
            @Field("annuity_pension") String annuity_pension,
            @Field("saf") String saf,
            @Field("leave_encash") String leave_encash,
            @Field("retren_comp") String retren_comp,
            @Field("vr_comp") String vr_comp,
            @Field("others") String others,
            @Field("notes") String notes);


    @FormUrlEncoded
    @POST("insurance/update_user_insur")
    Call<AddInsuranceModel> callUpdateInsuranceService(@Field("user_id") String user_id,
                                                       @Field("ins_id") String ins_id,
                                                       @Field("family_id") String family_id,
                                                       @Field("ins_type") String type,
                                                       @Field("policy_name") String asset_name,
                                                       @Field("coverage") String cat_lev1_id,
                                                       @Field("annual_prem") String cat_lev2_id,
                                                       @Field("exp_incr") String cat_lev3_id,
                                                       @Field("policy_issue_date") String other_cat,
                                                       @Field("last_prem_date") String objective,
                                                       @Field("next_prem_date") String current_value,
                                                       @Field("prem_due_date") String annual_contr,
                                                       @Field("policy_end_date") String freq_of_contr,
                                                       @Field("maturity_date") String years_of_contr,
                                                       @Field("term_years") String years_to_maturity,
                                                       @Field("notes") String alloc_to_goal,
                                                       @Field("last_paid_date") String last_paid_date,
                                                       @Field("ins_freq") String ins_freq,
                                                       @Field("ins_sub_type") String ins_sub_type,
                                                       @Field("ins_prod_type") String ins_prod_type,
                                                       @Field("ins_sub_det") String ins_sub_det,
                                                       @Field("plan_type") String plan_type,
                                                       @Field("motor_type") String motor_type);


    @FormUrlEncoded
    @POST("expense/get_expense_analysis_by_user")
    Call<ExpensesAnalysisModel> callExpensesAnalysisService(
            @Field("user_id") String user_id,
            @Field("is_own_user") String yes);


    @FormUrlEncoded
    @POST("income/get_income_analysis_by_user")
    Call<IncomeAnalysisModel> callIncomeAnalysisService(
            @Field("user_id") String user_id);

//    @FormUrlEncoded
//    @POST("assets/get_asset_alloc_by_user")
//    Call<IncomeAnalysisModel> callAssetsAllocationAnalysisService(
//            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("goals/get_goal_details_by_user")
    Call<GoalAnalysisModel> callGoalAnalysisService(
            @Field("user_id") String user_id);

    /**
     * a
     * Expense Module services- retuns Expense category
     *
     * @return ExpensesDetailModel
     */
    @POST("categories/get_expense_categories")
    Call<ExpensesDetailModel> callExpensesCategoriesService();


//    @FormUrlEncoded
//    @POST("expense/get_user_expense")
//    Call<UpdateExpensesDetailModel> callGetExpensesService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("income/get_user_income")
    Call<GetIncomeModel> GetIncomeDetailService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("income/get_user_postret_income")
    Call<GetIncomeModel> GetIncomepostretDetailService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("income/get_ret_benefit_by_user")
    Call<RetirementBenifitModel> callGetRetirementBenefitDetailService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("expense/add_user_expense")
    Call<ExpensesSelectionAddModel> callGetExpensesAddModelService(@Field("user_id") String user_id,
                                                                   @Field("is_own_user") String is_own_user,
                                                                   @Field("level_0_ids") String level_0_ids,
                                                                   @Field("level_1_ids") String level_1_ids,
                                                                   @Field("level_2_ids") String level_2_ids,
                                                                   @Field("level_3_ids") String level_3_ids,
                                                                   @Field("family_id") String family_id);
//    @FormUrlEncoded
//    @POST("expense/add_user_expense")
//    Call<ExpensesUpdateModel> callGetExpUpdateModelService(@Field("user_id") String user_id,
//                                                           @Field("level_1_ids") String level_1_ids,
//                                                           @Field("level_2_ids") String level_2_ids,
//                                                           @Field("level_3_ids") String level_3_ids,
//                                                           @Field("is_own_user") String is_own_user,
//                                                           @Field("family_id") String family_id);
////                                                               @Field("exp_id") String exp_id);

    @FormUrlEncoded
    @POST("expense/get_user_expense")
    Call<GetExpensesDetailsModel> callGetExpensesDetailsService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("expense/add_user_expense")
    Call<ExpensesUpdateModel> callGetExpensesUpdateModelService(@Field("user_id") String user_id,
                                                                @Field("level_0_ids") String level_0_ids,
                                                                @Field("level_1_ids") String level_1_ids,
                                                                @Field("level_2_ids") String level_2_ids,
                                                                @Field("level_3_ids") String level_3_ids,
//                                                                @Field("exp_id") String exp_id,
                                                                @Field("exp_details") String exp_details,
                                                                @Field("overall_expense") String overall_expense,
                                                                @Field("is_own_user") String is_own_user,
                                                                @Field("family_id") String family_id);

    @FormUrlEncoded
    @POST("expense/get_user_postret_expense")
    Call<GetExpensesDetailsModel> GetPostRetirementExpensesService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("expense/add_user_postret_expense")
    Call<ExpensesSelectionAddModel> addPostRetirementExpensesService(@Field("user_id") String user_id,
                                                                     @Field("is_own_user") String is_own_user,
                                                                     @Field("level_0_ids") String level_0_ids,
                                                                     @Field("level_1_ids") String level_1_ids,
                                                                     @Field("level_2_ids") String level_2_ids,
                                                                     @Field("level_3_ids") String level_3_ids,
                                                                     @Field("family_id") String family_id);

    //
    @FormUrlEncoded
    @POST("expense/add_user_postret_expense")
    Call<ExpensesUpdateModel> addPostRetirementExpensesService(@Field("user_id") String user_id,
                                                               @Field("level_0_ids") String level_0_ids,
                                                               @Field("level_1_ids") String level_1_ids,
                                                               @Field("level_2_ids") String level_2_ids,
                                                               @Field("level_3_ids") String level_3_ids,
//                                                                @Field("exp_id") String exp_id,
                                                               @Field("exp_details") String exp_details,
                                                               @Field("overall_expense") String overall_expense,
                                                               @Field("is_own_user") String is_own_user,
                                                               @Field("family_id") String family_id);

    @FormUrlEncoded
    @POST("assets/get_networth_details_by_user")
    Call<NetworkAnalysisModel> callNetworkAnalysisService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("assets/get_asset_alloc_by_user")
    Call<AssestAnalysisModel> callAssetsAllocationAnalysisService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("assets/get_asset_analysis_by_user")
    Call<AssetAnalysisNewModel> callAssetAnalysisNewService(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("score/get_pp_score_by_user")
    Call<ScoreAnalysisModel> callPPScoreAnalysisService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("cash/get_cash_mang_by_user")
    Call<CashManagentAnalyisModel> callCashMangChartService(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("cash/get_emergency_fund")
    Call<EmergencyFundModel> callEmergencyFundChartService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("goals/get_goal_timeline_by_user")
    Call<GoalTimeLineModel> callGoalTimeService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("cash/get_cashflow_inout_flow_by_user")
    Call<GetcashflowinoutflowModel> callCashFlowAnalysisService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("cash/get_cashflow_by_asset_liabilities")
    Call<GetcashflowAssetflowModel> callCashFlowAssetService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("cash/get_cashflow_by_goal_investment")
    Call<GetcashflowGoalInvestmentModel> callCashFlowInvestService(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("cash/get_all_market_data")
    Call<GetAllMarketData> callGetAllMarketDataService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("cash/get_insurance_plan")
    Call<InsuranceChartModel> callinsurance_plan_Service(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("sas/get_sas_assumptions_by_user")
    Call<AssumptionsModel> callAssumptionsService(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("tax/get_tax_plan")
    Call<GetTaxPlanModels> callinsurance_tax_Service(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("tax/get_tax_by_user")
    Call<TaxCashFlowModel> callinsurance_tax_Cash_Flow_Service(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("sas/get_sas_selections_by_user")
    Call<SelectionsModel> callSelectionService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("sas/update_sas_selections")
    Call<SelectionsUpdateModel> callSelectionsUpdateService(
            @Field("user_id") String user_id,
            @Field("planned_retirement_age") String planned_retirement_age,
            @Field("life_expectancy_age") String life_expectancy_age,
            @Field("spouse_life_expectancy_age") String spouse_life_expectancy_age,
            @Field("spouse_retirement_age") String spouse_retirement_age,
            @Field("exp_tax_per") String exp_tax_per,
            @Field("tax_cons") String tax_cons,
            @Field("duration") String duration,
            @Field("input_details") String input_details,
            @Field("processing") String processing,
            @Field("output_report") String output_report,
            @Field("debt_ratio") String debt_ratio,
            @Field(" first_home") String first_home

    );

    @FormUrlEncoded
    @POST("push/get_noficiations_by_user")
    Call<NotificationsModel> callNotificationService(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("prompt/get_personal_details_empty_field")
    Call<PersonalPromptModel> callAlertPromptService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/get_tax_card_display")
    Call<CardResponse> getCardPermission(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("sas/update_sas_assumptions")
    Call<AsssumptionsUpdateModel> updateAssumptionsService(
            @Field("user_id") String user_id,
            @Field("sas_details") String jsonObj);


    @FormUrlEncoded
    @POST("risk_prof/get_risk")
    Call<RiskProfileModel> getRiskProfileService(
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("risk_prof/add_risk_prof_by_score")
    Call<RiskProfileByScoreModel> getRiskProfileByScore(
            @Field("user_id") String user_id, @Field("risk_details") String s);

    @FormUrlEncoded
    @POST("prompt/get_all_alerts")
    Call<AlertsModel> callAlertsService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/get_all_empty_field")
    Call<PromptsModel> callPromptsService(@Field("user_id") String user_id);

//    @FormUrlEncoded
//    @POST("risk/get_risk_score_by_user")
//    Call<RiskScoreForUserModel> callGetRiskScoreForUser(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("recommend/get_all_recommendations")
    Call<RecommendData> triggerRecommendationService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("decipro/get_outstanding_balance")
    Call<OutstandingBalModel> callOutStandingBalService(@Field("loan_amount") String loan_amount,
                                                        @Field("int_rate") String int_rate,
                                                        @Field("tot_tenure") String tot_tenure,
                                                        @Field("tenure_completed") String tenure_completed,
                                                        @Field("emi") String emi
    );


    @FormUrlEncoded
    @POST("decipro/get_lease_payment")
    Call<CalculateLeasePaymentModel> callCalculateLeasePayment(@Field("car_pur_price") String car_pur_price,
                                                               @Field("cap_cost") String cap_cost,
                                                               @Field("cap_cost_redu") String cap_cost_redu,
                                                               @Field("resi_val") String resi_val,
                                                               @Field("no_of_months") String no_of_months,
                                                               @Field("int_rate") String int_rate,
                                                               @Field("sales_tax") String sales_tax
    );

    @FormUrlEncoded
    @POST("decipro/get_loan_recommendation")
    Call<LoanRecomendationModel> callLoanRecomendationService(@Field("curr_ln_out_bal") String curr_ln_out_bal,
                                                              @Field("curr_ln_int_rate") String curr_ln_int_rate,
                                                              @Field("curr_ln_tenure") String curr_ln_tenure,
                                                              @Field("curr_ln_bal_tenure") String curr_ln_bal_tenure,
                                                              @Field("curr_ln_emi") String curr_ln_emi,
                                                              @Field("new_ln_int_rate") String new_ln_int_rate,
                                                              @Field("new_ln_tenure") String new_ln_tenure,
                                                              @Field("new_ln_emi") String new_ln_emi,
                                                              @Field("loan_st_dt") String loan_st_dt,
                                                              @Field("first_emi_paid_dt") String first_emi_paid_dt,
                                                              @Field("last_emi_paid_dt") String last_emi_paid_dt,
                                                              @Field("next_emi_due_dt") String next_emi_due_dt,
                                                              @Field("final_emi_payment_dt") String final_emi_payment_dt,
                                                              @Field("exit_charge") String exit_charge,
                                                              @Field("entry_charge") String entry_charge
    );

    @FormUrlEncoded
    @POST("ratio/get_finance_ratios_by_user")
    Call<FinanceRatioModel> getFinanceRatioService(
            @Field("user_id") String user_id);

    @FormUrlEncoded

    @POST("faq/get_faq")
    Call<Faqmodel> getFAQService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("users/change_password")
    Call<ChangepwdModel> ChangePassword(@Field("user_id") String user_id, @Field("old_password") String old_password,
                                        @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("vendor/get_vendor")
    Call<VendorDetailsModel> getVendorDetailsService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("gloss/get_gloss")
    Call<GlossariesModel> getGlossariesService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("common/add_feedback_by_user")
    Call<Feedbackmodel> FeedbackaddService(@Field("user_id") String user_id, @Field("name") String name,
                                           @Field("email") String email, @Field("comments") String comments);

    @FormUrlEncoded
    @POST("quiz/get_quiz_by_topic")
    Call<Quizmodel> getquizbytopic(
            @Field("quiz_topic") String quiz_topic);

    @POST("quiz/get_quiz_topics")
    Call<GetQuizTopics> getQuizService();

    @FormUrlEncoded
    @POST("common/get_links")
    Call<Linksmodel> getLinksService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("cash/get_income_expense_cashflow_by_user")
    Call<IncomeExpenseCashFlowModel> getIncomeExpenseCashflowService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("decipro/get_amortization_schedule")
    Call<AmortizationScheduleModel> getAmortizationSchedule(
            @Field("out_bal") String outStanding_balance,
            @Field("rate") String rate,
            @Field("tenure") String tenure,
            @Field("service_tax_rate") String service_tax_rate
    );

    @FormUrlEncoded
    @POST("quiz/get_quiz")
    Call<Quizmodel> getQuizService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("decipro/get_loan_comparison")
    Call<LoanComparisonModel> getLoanComparisonService(

            @Field("ln1_fin_val") String FinanceValue1,
            @Field("ln1_rate") String loan1Rate,
            @Field("ln1_tenure") String loan1Tenure,
            @Field("ln1_emi") String loan1EMI,
            @Field("ln1_assoc_charges") String associatedCharges1,
            @Field("ln1_resi_val") String loan1Residual_val,
            @Field("ln2_fin_val") String FinanceValue2,
            @Field("ln2_rate") String loan2Rate,
            @Field("ln2_tenure") String loan2Tenure,
            @Field("ln2_emi") String loan2EMI,
            @Field("ln2_assoc_charges") String associatedCharges2,
            @Field("ln2_resi_val") String loan2Residual_val,
            @Field("ln3_fin_val") String FinanceValue3,
            @Field("ln3_rate") String loan3Rate,
            @Field("ln3_tenure") String loan3Tenure,
            @Field("ln3_emi") String loan3EMI,
            @Field("ln3_assoc_charges") String associatedCharges3,
            @Field("ln3_resi_val") String loan3Residual_val

    );

    @FormUrlEncoded
    @POST("decipro/get_deposit_comparison")
    Call<DepositCompModel> getDepositComparisonService(
            @Field("dp1_init_val") String dp1_init_val,
            @Field("dp1_period_val") String dp1_period_val,
            @Field("dp1_tenure") String dp1_tenure,
            @Field("dp1_rate") String dp1_rate,
            @Field("dp2_init_val") String dp2_init_val,
            @Field("dp2_period_val") String dp2_period_val,
            @Field("dp2_tenure") String dp2_tenure,
            @Field("dp2_rate") String dp2_rate,
            @Field("dp3_init_val") String dp3_init_val,
            @Field("dp3_period_val") String dp3_period_val,
            @Field("dp3_tenure") String dp3_tenure,
            @Field("dp3_rate") String dp3_rate

    );

    @FormUrlEncoded
    @POST("decipro/get_time_value_of_money")
    Call<TimeValueOfMoneyModel> getTimeValueOfMoneyService(
            @Field("flag") String flag,
            @Field("tenure") String tenure,
            @Field("tenure_period") String tenure_period,
            @Field("rate") String rate,
            @Field("pv") String presentValue,
            @Field("fv") String futureValue,
            @Field("pmt") String pmt,
            @Field("pmt_type") String paymentType,
            @Field("comp_period") String compoundingPeriodPerYear,
            @Field("pay_period") String payementPeriodPerYear
    );



   /* //Retrofit2.0
    @Multipart
    @POST("document/add_document")
    Call<DocumentModel> callAddFileService(@Part("user_id") String user_id,
                                           @Part("flag") String flag,
                                           @Part("digital_version") String digital_version,
                                           @Part("doc_tag") String doc_tag,
                                           @Part("add_document") RequestBody reqFile);*/


    //Retrofit1.9
    @retrofit.http.Multipart
    @retrofit.http.POST("/document/add_document")
    void callAddFileService(@retrofit.http.Part("user_id") String user_id,
                            @retrofit.http.Part("flag") String flag,
                            @retrofit.http.Part("digital_version") String digital_version,
                            @retrofit.http.Part("doc_tag") String doc_tag,
                            @retrofit.http.Part("add_document") TypedFile typedFile,
                            @retrofit.http.Part("X-API-KEY") String apikey,
                            @retrofit.http.Part("sub_digital_version") String sub_digital_version,
                            @retrofit.http.Part("product") String product,
                            retrofit.Callback<DocumentModel> callback);

    @FormUrlEncoded
    @POST("document/get_documents_by_user")
    Call<GetDocumentModels> callget_documents_by_user(@Field("user_id") String user_id,
                                                      @Field("digi_ver") String typeofdigi);

    @FormUrlEncoded
    @POST("document/get_all_documents_by_user")
    Call<GetDocumentModels> callget_all_documents_by_user(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("document/delete_user_document")
    Call<DeleteModels> call_delete_documents_by_user(@Field("user_id") String user_id,
                                                     @Field("doc_id") String postion);

    @FormUrlEncoded
    @POST("schedule/get_schedules_by_user")
    Call<ScheduleModel> call_schedules_by_user(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("schedule/update_schedule_info_by_id")
    Call<UpdateScheduleModel> call_update_schedule(@Field("id") String id,
                                                   @Field("flag") String flag,
                                                   @Field("paid_date") String paid_date);

    @FormUrlEncoded
    @POST("recommend/get_networth_recom")
    Call<Networthmodel> getNetworthRecommendation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("recommend/get_invest_plan_recom")
    Call<InvestmentPlanModel> getInvestmentPlan(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("users/update_user_profile")
    Call<ProfileModels> callUpdateProfileService(@Field("user_id") String user_id,
                                                 @Field("name") String name,
                                                 @Field("last_name") String lastname,
                                                 @Field("email") String Email,
                                                 @Field("country_code") String country_code,
                                                 @Field("phone") String phone,
                                                 @Field("address_home") String address_home,
                                                 @Field("ah_city") String ah_city,
                                                 @Field("ah_state") String ah_state,
                                                 @Field("ah_country") String ah_country,
                                                 @Field("ah_zipcode") String ah_zipcode,
                                                 @Field("alias_name") String mAliasname,

                                                 @Field("ah_res_no") String ah_res_no,
                                                 @Field("ah_res_name") String ah_res_name,
                                                 @Field("ah_road_street") String ah_road_street,
                                                 @Field("ah_locality_area") String ah_locality_area);


    @FormUrlEncoded
    @POST("users/get_user_profile_details")
    Call<GetProfiledata> callgetProfileService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("goals/get_ret_goals_by_user")
    Call<RetirementModel> callget_ret_goals_by_user(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("goals/get_goal_plan")
    Call<GoalPlanModel> callGetGoalService(@Field("user_id") String user_id,
                                           @Field("goal_id") String goal_id);

    @FormUrlEncoded
    @POST("recommend/get_goal_recomm")
    Call<Goalmodel> getGoalRecommendation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("know/get_knowledge")
    Call<Knowledgemodel> getKnowledgeService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("decipro/get_goal_affordability")
    Call<GoalAffordabilityModels> getGoalAffordability(@Field("goal_type") String goal_type,
                                                       @Field("timeframe") String timeframe,
                                                       @Field("current_cost") String current_cost,
                                                       @Field("expt_incr") String expt_incr,
                                                       @Field("annual_savings") String annual_savings,
                                                       @Field("current_savings") String current_savings,
                                                       @Field("expected_return") String expected_return);

    @FormUrlEncoded
    @POST("decipro/get_loan_eligibility")
    Call<LoanEligibilityModel> getloaneligibility(@Field("loan_type") String loan_type,
                                                  @Field("fund_req") String fund_req,
                                                  @Field("down_payment") String down_payment,
                                                  @Field("loan_req") String loan_req,
                                                  @Field("loan_tenure") String loan_tenure,
                                                  @Field("int_rate") String int_rate,
                                                  @Field("payment_capacity") String payment_capacity,
                                                  @Field("tot_inc") String tot_inc,
                                                  @Field("tot_payment") String tot_payment,
                                                  @Field("credit_score") String credit_score);

    @FormUrlEncoded
    @POST("survey/get_active_survey_by_user")
    Call<Surveymodel> getSurveyService(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("survey/add_survey_by_user")
    Call<Surveytomodel> getSurveytoService(
            @Field("user_id") String user_id,
            @Field("survey_id") String survey_id,
            @Field("option") String option);

    @FormUrlEncoded
    @POST("risk/get_risk_score_by_user")
    Call<RiskDailyUpdateModel> getRiskSoreByUser(
            @Field("user_id") String user_id,
            @Field("version") String version);


    @FormUrlEncoded
    @POST("recommend/get_cash_mang_recom")
    Call<Cashmodel> getCashManagementRecommendation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("recommend/get_invest_plan_recomm")
    Call<Saveinvestmodel> getSaveInvestmentRecommendation(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/get_home_cards")
    Call<HomeCardsModel> getHomeAllCardsService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("decipro/get_house_buy_rent_emi")
    Call<EmiModel> getEMIservice(@Field("loan_amt") String loan_amt,
                                 @Field("loan_tenure") String loan_tenure,
                                 @Field("loan_int_rate") String loan_int_rate);

    @FormUrlEncoded
    @POST("decipro/get_house_buy_rent")
    Call<BuyVsRentModel> getBuyVsRentservice(@Field("city_type") String city_type,
                                             @Field("gross_salary") String gross_salary,
                                             @Field("tax_slab") String tax_slab,
                                             @Field("oppur_cost") String oppur_cost,
                                             @Field("planned_occupation") String planned_occupation,
                                             @Field("loan_req") String loan_req,
                                             @Field("property_price") String property_price,
                                             @Field("status") String status,
                                             @Field("year_to_posses") String year_to_posses,
                                             @Field("prop_appre") String prop_appre,
                                             @Field("mortage_pay") String mortage_pay,
                                             @Field("real_estate_tax") String real_estate_tax,
                                             @Field("main_repair_annu") String main_repair_annu,
                                             @Field("util_annu") String util_annu,
                                             @Field("insurance") String insurance,
                                             @Field("int_foregone_down_pay") String int_foregone_down_pay,
                                             @Field("current_rent") String current_rent,
                                             @Field("rent_sec_dep") String rent_sec_dep,
                                             @Field("rent_esc") String rent_esc,
                                             @Field("rent_util") String rent_util,
                                             @Field("rent_insurance") String rent_insurance,
                                             @Field("int_fgone_sec_dep_rate") String int_fgone_sec_dep_rate,
                                             @Field("down_pay") String down_pay,
                                             @Field("loan_amt") String loan_amt,
                                             @Field("loan_tenure") String loan_tenure,
                                             @Field("loan_int_rate") String loan_int_rate,
                                             @Field("loan_prop_usage") String loan_prop_usage,
                                             @Field("tax_24") String tax_24,
                                             @Field("tax_80c") String tax_80c);


    @FormUrlEncoded
    @POST("oauth/token")
    Call<AccessToken> getNewAccessToken(
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("oauth/token")
    Call<AccessToken> getRefreshAccessToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);


    @FormUrlEncoded
    @POST("insurance/get_all_contingency_plan")
    Call<Motor_Proper_Health_Model> callGetPropertyService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("users/get_version")
    Call<VersionModel> callVersionService(@Field("version") String version);

//    @FormUrlEncoded
//    @POST("users/update_user_payment_details")
//    Call<PaymentModel> callPaymentService(
//            @Field("user_id") String user_id,
//            @Field("user_paid_type") String user_paid_type,
//            @Field("last_paid_amount") String last_paid_amount,
//            @Field("validity_months") String validity_months,
//            @Field("transaction_id") String transaction_id);

    @FormUrlEncoded
    @POST("prompt/get_prompt_statements")
    Call<GetPromptModel> callGetPromptService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/update_insert_flag")
    Call<InsertModel> callUpdateInsertFlagService(@Field("user_id") String user_id,
                                                  @Field("table") String table,
                                                  @Field("user_visited_flag") String user_visited_flag,
                                                  @Field("update_flag") String update_flag);

    @FormUrlEncoded
    @POST("users/get_all_company_categories")
    Call<CompanyCategorieModel> callGetCompanyCategotiesService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("tax/add_user_prepaid_tax")
    Call<TaxPrepaidmodel> callAddTaxPrepaidService(@Field("user_id") String user_id,
                                                   @Field("total_advance_tax_paid") String total_advance_tax_paid,
                                                   @Field("total_self_assessment_tax_paid") String total_self_assessment_tax_paid,
                                                   @Field("total_tds_deducted") String total_tds_deducted,
                                                   @Field("total_tcs_deducted") String total_tcs_deducted,
                                                   @Field("mat_credit") String mat_credit,
                                                   @Field("amt_credit") String amt_credit,
                                                   @Field("notes") String notes);

    @FormUrlEncoded
    @POST("tax/get_prepaid_tax_by_user")
    Call<TaxPrepaidmodel> callGetTaxPrepaidService(@Field("user_id") String user_id);

  /*  @FormUrlEncoded
    @POST("tax/get_tax_by_user_new")
    Call<TaxPromptNewModel> callTaxPromptService(@Field("user_id") String user_id);*/

    @FormUrlEncoded
    @POST("tax/get_tax_by_user_by_financial_year")
    Call<TaxPromptNewModel> callTaxPromptService(@Field("user_id") String user_id, @Field("financial_year") String financial_year);


    @FormUrlEncoded
    @POST("tax/get_tax_by_user_by_financial_year")
    Call<TaxPromptNewModel> callTaxPromptService_new(@Field("user_id") String user_id, @Field("financial_year") String financial_year);

    @FormUrlEncoded
    @POST("decipro/get_car_buy_lease")
    Call<CarVsLeaseModel> getCarVsLeaseservice(@Field("city_type") String city_type,
                                               @Field("tax_slab") String tax_slab,
                                               @Field("oppur_cost") String oppur_cost,
                                               @Field("planned_occupation") String planned_occupation,
                                               @Field("loan_req") String loan_req,
                                               @Field("car_pur_price") String car_pur_price,
                                               @Field("tot_upf_sec_pay") String tot_upf_sec_pay,
                                               @Field("mon_lease_pay") String mon_lease_pay,
                                               @Field("down_pay") String down_pay,
                                               @Field("loan_amt") String loan_amt,
                                               @Field("loan_tenure") String loan_tenure,
                                               @Field("loan_int_rate") String loan_int_rate,
                                               @Field("main_repair") String main_repair,
                                               @Field("fuel_run_exp") String fuel_run_exp,
                                               @Field("insurance") String insurance,
                                               @Field("lease_tax") String lease_tax,
                                               @Field("lease_ter_exp") String lease_ter_exp);


    //TaxPromptChartFragment
    @FormUrlEncoded
    @POST("prompt/add_update_tax_prompt_statements")
    Call<TaxPromptAnserModel> callUpdateFieldService(@Field("user_id") String user_id,
                                                     @Field("corresponding_table") String corresponding_table,
                                                     @Field("field_name") String field_name,
                                                     @Field("field_value") String field_value,
                                                     @Field("question_id") String question_id,
                                                     @Field("encrypt_flag") String encrypt_flag,
                                                     @Field("clear_flag") String clearflag,
                                                     @Field("clear_details") String clear_details);

    @FormUrlEncoded
    @POST("prompt/get_tax_prompt_statements")
    Call<TaxPromptModel> callGetTaxPromptService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/delete_tax_prompt_statements")
    Call<DeleteTaxStatemetModel> deleteTaxPromptStatement(@Field("user_id") String userId,
                                                          @Field("ques_id") String ques_id,
                                                          @Field("flag") String flag);

//TaxPromptChartFragment

    //commonpayment getservice
    @FormUrlEncoded
    @POST("payment/get_payment_details_by_user")
    Call<CommonPaymentModel> get_payment_details_by_user(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("prompt/get_life_events")
    Call<LifeEventModel> get_LifeEventService(@Field("user_id") String userId);


    //Tax File Chart Conversation
    @FormUrlEncoded
    @POST("prompt/add_update_tax_file_prompt_statements")
    Call<TaxPromptAnserModel> callTaxFileUpdateFieldService(@Field("user_id") String user_id,
                                                            @Field("corresponding_table") String corresponding_table,
                                                            @Field("field_name") String field_name,
                                                            @Field("field_value") String field_value,
                                                            @Field("question_id") String question_id,
                                                            @Field("encrypt_flag") String encrypt_flag,
                                                            @Field("clear_flag") String clearflag,
                                                            @Field("clear_details") String clear_details);

    @FormUrlEncoded
    @POST("prompt/get_tax_file_prompt_statements")
    Call<TaxPromptModel> callGetTaxFileService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/delete_tax_prompt_efill_visited_questions")
    Call<DeleteTaxStatemetModel> deleteTaxFileStatement(@Field("user_id") String userId,
                                                        @Field("ques_id") String ques_id,
                                                        @Field("flag") String flag);
//Tax File Chart Conversation

    @FormUrlEncoded
    @POST("prompt/update_declaration_details")
    Call<DeclarationModel> callAddTaxFileDeclarationService(@Field("user_id") String user_id,
                                                            @Field("first_name") String first_name,
                                                            @Field("surname") String surname,
                                                            @Field("place") String place,
                                                            @Field("date") String date,
                                                            @Field("pan") String pan,
                                                            @Field("capacity") String capacity);

    @FormUrlEncoded
    @POST("prompt/get_declaration_details")
    Call<DeclarationModel> callGetTaxFileDeclarationService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("taxefill/get_tax_file_checklist")
    Call<ChecklistModel> getCheckListService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("document/get_documents_by_sub_digi_ver")
    Call<GetChecklistModel> getCheckListDocumentService(@Field("user_id") String user_id,
                                                        @Field("sub_digi_ver") String sub_digi_ver);


    //TaxFileAdditionalChartConversation
    @FormUrlEncoded
    @POST("prompt/add_update_tax_efill_product_prompt_statements")
    Call<TaxPromptAnserModel> callTaxFileProductUpdateFieldService(@Field("user_id") String user_id,
                                                                   @Field("corresponding_table") String corresponding_table,
                                                                   @Field("field_name") String field_name,
                                                                   @Field("field_value") String field_value,
                                                                   @Field("question_id") String question_id,
                                                                   @Field("encrypt_flag") String encrypt_flag,
                                                                   @Field("clear_flag") String clearflag,
                                                                   @Field("clear_details") String clear_details);

    @FormUrlEncoded
    @POST("prompt/get_tax_efill_product_prompt_statements")
    Call<TaxPromptModel> callGetTaxFileProductService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/delete_tax_efill_product_prompt_visited_questions")
    Call<DeleteTaxStatemetModel> deleteTaxFileProductStatement(@Field("user_id") String userId,
                                                               @Field("ques_id") String ques_id,
                                                               @Field("flag") String flag);
    //TaxFileAdditionalChartConversation


    @FormUrlEncoded
    @POST("taxefill/get_tax_file_data_summary")
    Call<TaxFileSummaryModel> callGetTaxFileSummaryService(@Field("user_id") String user_id);


    //taxfilepayment
    @FormUrlEncoded
    @POST("payment/get_tax_file_payment_details_by_user")
    Call<TaxPaymentModels> get_tax_payment_details_by_user(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("payment/get_payment_type_by_user")
    Call<UpgradePaymentModel> get_common_payment_summary(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("taxefill/get_tax_doc_verified_status")
    Call<ConformationModel> callGetTaxfileConformationService(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("taxefill/create_xml_new")
    Call<CredencialModel> callAddTaxfileCredentialsService(@Field("user_id") String user_id,
                                                           @Field("inc_tax_credentails") String inc_tax_credentails,
                                                           @Field("aadhar_pan_link") String aadhar_pan_link,
                                                           @Field("tax_file_from_us") String tax_file_from_us,
                                                           @Field("cpc_verification") String cpc_verification);

    //Here get all the screen session maintanace
    @FormUrlEncoded
    @POST("taxefill/get_tax_file_user_status")
    Call<UserStatusModel> callGetTaxfileUserStatus(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("taxefill/add_user_tax_confirmation")
    Call<AdharModel> calladdTaxConfirmationStatus(@Field("user_id") String userId,
                                                  @Field("confirmation_text") String confirmation_text,
                                                  @Field("itr_processing_date") String itr_processing_date,
                                                  @Field("refund_received_date") String refund_received_date,
                                                  @Field("intimation_received_date") String intimation_received_date);

    @FormUrlEncoded
    @POST("taxefill/get_user_tax_confirmation")
    Call<TaxConfirmationModel> callgetTaxConfirmationStatus(@Field("user_id") String userId);

    //    @FormUrlEncoded
//    @POST("taxefill/parse_form16_pdf")
//    Call<ParseFormModel> callgetTaxFilingParseForm(@Field("user_id") String userId,
//                                                   @Field("doc_id")String doc_id,
//                                                   @Field("password_protect")String password_protect,
//                                                   @Field("password")String password);
    @FormUrlEncoded
    @POST("taxefill/validate_form16_file")
    Call<TaxValidationModel> callgetTaxFilingParseSuccess(@Field("user_id") String userId,
                                                          @Field("doc_id") String doc_id,
                                                          @Field("password_protect") String password_protect,

                                                          @Field("password") String password);

    @FormUrlEncoded
    @POST("document/delete_user_document")
    Call<DeleteValidationFormModel> callgetTaxFilingParseDelete(@Field("user_id") String userId,
                                                                @Field("doc_id") String doc_id);

    @FormUrlEncoded
    @POST("taxefill/add_update_prev_tax_file_status")
    Call<TaxFilePreviousUpdateModel> callTaxFilingUpdatePreviousTaxFile(@Field("user_id") String userId,
                                                                        @Field("is_file") String doc_id);

    @FormUrlEncoded
    @POST("taxefill/parse_form16_pdf")
    Call<ParseFormSuccessModel> callgetTaxFilingParseFormSucess(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("taxefill/validate_form26as_file")
    Call<TwentySixParseValidateModel> callgetTaxFilingParse26AS(@Field("user_id") String userId,
                                                                @Field("doc_id") String doc_id,
                                                                @Field("password_protect") String password_protect,
                                                                @Field("password") String password);


    //Tax File Initial Conversation
    @FormUrlEncoded
    @POST("prompt/add_update_tax_file_initial_prompt_statements")
    Call<TaxPromptAnserModel> callTaxFileInitialUpdateFieldService(@Field("user_id") String user_id,
                                                                   @Field("corresponding_table") String corresponding_table,
                                                                   @Field("field_name") String field_name,
                                                                   @Field("field_value") String field_value,
                                                                   @Field("question_id") String question_id,
                                                                   @Field("encrypt_flag") String encrypt_flag,
                                                                   @Field("clear_flag") String clearflag,
                                                                   @Field("clear_details") String clear_details);

    @FormUrlEncoded
    @POST("prompt/get_tax_file_initial_prompt_statements")
    Call<TaxPromptModel> callInitialGetTaxFileService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/delete_tax_file_initial_statements_visited_ques")
    Call<DeleteTaxStatemetModel> deleteInitialTaxFileStatement(@Field("user_id") String userId,
                                                               @Field("ques_id") String ques_id,
                                                               @Field("flag") String flag);
//Tax File Initial Conversation


    @FormUrlEncoded
    @POST("taxefill/add_update_tax_file_page_visit")
    Call<AddSessionModel> AddTaxfileSessionService(@Field("user_id") String userId,
                                                   @Field("corresponding_table") String corresponding_table,
                                                   @Field("field_name") String field_name,
                                                   @Field("field_value") String field_value);

    @FormUrlEncoded
    @POST("taxefill/validate_tax_plan")
    Call<PlanAmountModel> callGetPlanAmountService(@Field("user_id") String user_id);


    //Tax File House Property Conversation
    @FormUrlEncoded
    @POST("prompt/add_update_property_prompt_statements")
    Call<TaxPromptAnserModel> callTaxFileHouseUpdateFieldService(@Field("user_id") String user_id,
                                                                 @Field("corresponding_table") String corresponding_table,
                                                                 @Field("field_name") String field_name,
                                                                 @Field("field_value") String field_value,
                                                                 @Field("question_id") String question_id,
                                                                 @Field("encrypt_flag") String encrypt_flag,
                                                                 @Field("clear_flag") String clearflag,
                                                                 @Field("clear_details") String clear_details);

    @FormUrlEncoded
    @POST("prompt/get_property_prompt_statements")
    Call<TaxPromptModel> callInitialGetHouseTaxFileService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/delete_property_statements_visited_ques")
    Call<DeleteTaxStatemetModel> deleteHouseTaxFileStatement(@Field("user_id") String userId,
                                                             @Field("ques_id") String ques_id,
                                                             @Field("flag") String flag);
//Tax File House Property Conversation


    //Tax File Credential Conversation
    @FormUrlEncoded
    @POST("prompt/add_update_tax_credentails_prompt_statements")
    Call<TaxPromptAnserModel> callTaxFileCredentialUpdateFieldService(@Field("user_id") String user_id,
                                                                      @Field("corresponding_table") String corresponding_table,
                                                                      @Field("field_name") String field_name,
                                                                      @Field("field_value") String field_value,
                                                                      @Field("question_id") String question_id,
                                                                      @Field("encrypt_flag") String encrypt_flag,
                                                                      @Field("clear_flag") String clearflag,
                                                                      @Field("clear_details") String clear_details);

    @FormUrlEncoded
    @POST("prompt/get_tax_credentails_prompt_statements")
    Call<TaxPromptModel> callGetCredentialTaxFileService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("prompt/delete_tax_credentails_visited_ques")
    Call<DeleteTaxStatemetModel> deleteCredentialTaxFileStatement(@Field("user_id") String userId,
                                                                  @Field("ques_id") String ques_id,
                                                                  @Field("flag") String flag);

    //Tax File Credential Conversation
    @FormUrlEncoded
    @POST("taxefill/validate_form16_plus_file")
    Call<TaxValidationModelMultiple> callgetTaxFilingMultipleParseSuccess(@Field("user_id") String userId,
                                                                          @Field("doc_id") String doc_id,
                                                                          @Field("password_protect") String password_protect,
                                                                          @Field("password") String password);

    @FormUrlEncoded
    @POST("common/get_all_form16_links")
    Call<TaxFileFormInstructionModel> callGetInstructionFormService(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("taxefill/generate_itr1_pdf")
    Call<TaxFreeUserPdfModel> callGetFreeUserPdfService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("taxefill/parse_form16_plus_pdf")
    Call<ParseFormSuccessModel> callgetTaxFilingParseFormMultipleSucess(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("taxefill/create_xml_new")
    Call<CredencialModel> callCreateXmlfileService(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("admin/update_users_tax_file_state")
    Call<TaxFileResetModel> callGetResetTaxfileService(@Field("user_id") String user_id);

    //planning section
    @FormUrlEncoded
    @POST("prompt/get_tax_prompt_statements_138")
    Call<TaxPromptModels> callGetTaxPromptServiceNew(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("tax/add_user_tax_plan_tab_visited_status")
    Call<AddSessionModel> AddTaxPlanninngSection(@Field("user_id") String userId,
                                                 @Field("tab_name") String tab_name);

    @FormUrlEncoded
    @POST("prompt/delete_tax_prompt_statements_138")
    Call<DeleteTaxStatemetModel> deleteTaxPromptStatementNew(@Field("user_id") String userId,
                                                             @Field("ques_id") String ques_id,
                                                             @Field("flag") String flag);
//planning section


    //filing section

    @FormUrlEncoded
    @POST("prompt/get_tax_file_prompt_statements_138")
    Call<TaxFilingModel> callGetTaxFileServiceNew(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("tax/add_user_tax_efill_tab_visited_status")
    Call<AddSessionModel> AddTaxFilingSection(@Field("user_id") String userId,
                                              @Field("tab_name") String tab_name);

    @FormUrlEncoded
    @POST("prompt/delete_tax_prompt_efill_visited_questions_138")
    Call<DeleteTaxStatemetModel> deleteTaxFileStatementNew(@Field("user_id") String userId,
                                                           @Field("ques_id") String ques_id,
                                                           @Field("flag") String flag);
    //filing section


    @FormUrlEncoded
    @POST("users/get_promo_code_details")
    Call<PromoCodeModel> getPromoCodeService(@Field("user_id") String userId,
                                             @Field("promo_code") String promo_code);

    @FormUrlEncoded
    @POST("users/get_discount_by_company")
    Call<DiscountModel> getDiscountService(@Field("user_id") String userId);


    @FormUrlEncoded
    @POST("investment/get_investment_by_user")
    Call<InvestmentPlan> getInvesmentPlaning(@Field("user_id") String user_id);




}
