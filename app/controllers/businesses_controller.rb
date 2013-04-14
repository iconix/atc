class BusinessesController < ApplicationController

  def show
    @business = Business.find(params[:id])
		@deals = @business.deals
  end

  def new
    @business = Business.new
  end

  def create
    @business = Business.new(params[:business])
    if @business.save
			sign_in @business
      flash[:success] = "Welcome to Around the Corner!"
      redirect_to @business
    else
      render 'new'
    end
  end

  def edit
    @business = Business.find(params[:id])
  end

  def update
    @business = Business.find(params[:id])
    if params[:business][:password].blank?
      @business.update_attribute(:name, params[:business][:name])
      @business.update_attribute(:email, params[:business][:email])
      flash[:success] = "Profile updated"
      sign_in @business
      redirect_to @business
    else
      if @business.update_attributes(params[:business])
        flash[:success] = "Profile updated"
        sign_in @business
        redirect_to @business
      else
        render 'edit'
      end
    end
  end
end
