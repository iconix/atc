class BusinessesController < ApplicationController
  before_filter :signed_in_business, 	only: [:show, :edit, :update, :index, :destroy]
	before_filter :correct_business, 		only: [:edit, :update, :destroy]
	#May be dangerous to allow all users to destroy?
	#before_filter :admin_user,					only: :destroy 

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
  end

  def update
    unless params[:cancel].blank?
      redirect_to @business
      return
    end

    if params[:business][:password].blank?
      @business.update_attribute(:name, params[:business][:name])
      @business.update_attribute(:email, params[:business][:email])
      @business.update_attribute(:address, params[:business][:address])
      @business.update_attribute(:phoneNumber, params[:business][:phoneNumber])
      @business.update_attribute(:imageURL, params[:business][:imageURL])
      @business.update_attribute(:image, params[:business][:image])
      @business.update_attribute(:image_delete, params[:business][:image_delete])
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
  
  def index
    if current_business.admin?
      @businesses = Business.all
    end
  end
  
  def destroy
		# ADD CODE TO PREVENT ADMIN FROM DELETING HIMSELF OR OTHER ADMIN
	  Business.find(params[:id]).destroy
    flash[:success] = "Business destroyed."
    redirect_to businesses_url
	end

	private
		def correct_business
			@business = Business.find(params[:id])
			redirect_to(root_path) unless (current_business?(@business) || current_business.admin?)
		rescue
			redirect_to(root_path)
		end

		def admin_user
      redirect_to(root_path) unless current_business.admin?
    end
end
