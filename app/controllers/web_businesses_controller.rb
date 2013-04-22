class WebBusinessesController < ApplicationController
  before_filter :signed_in_business

  def show
    @web_business = WebBusiness.find(params[:id])
		@deals = @web_business.deals
  end

  def edit
    @web_business = WebBusiness.find(params[:id])
  end

  def index
    if current_business.admin?
      @web_businesses = WebBusiness.all
    end
  end

  def update
    @web_business = WebBusiness.find(params[:id])

    unless params[:cancel].blank?
      redirect_to @web_business
      return
    end

    if @web_business.update_attributes(params[:web_business])
      flash[:success] = "Profile updated"
      sign_in @web_business
      redirect_to @web_business
    else
      render 'edit'
    end
  end

  def destroy
    WebBusiness.find(params[:id]).destroy
    flash[:success] = "Web Business destroyed."
    redirect_to web_businesses_url
  end
end
