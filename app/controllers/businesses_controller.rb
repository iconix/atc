class BusinessesController < ApplicationController
  before_filter :signed_in_business, 	only: [:show, :edit, :update, :index, :destroy]
	before_filter :correct_business, 		only: [:show, :edit, :update, :destroy]
	before_filter :session_created, only: [:new, :create]
	#May be dangerous to allow all users to destroy?
	#before_filter :admin_user,					only: :destroy 

	def stats
		@business = Business.find(params[:id])

    # heatmap manipulations
    @start_date = Coordinate.minimum("time").getlocal.strftime("%m/%d/%Y %I:%M %p")
    @end_date = Coordinate.maximum("time").getlocal.strftime("%m/%d/%Y %I:%M %p")

		raw_data = JSON.parse(Coordinate.select("latitude, longitude").to_json)
    raw_data.each do |d|
      d["count"] = "1"
    end
    # formatting for javascript
    @data = raw_data.to_json.gsub('"', '').gsub('latitude', 'lat').gsub('longitude', 'lng')
    @size = raw_data.count
	end

  def stats_update
    @business = Business.find(params[:id])
    @start_date = params[:time][:start]
    @end_date = params[:time][:end]

    zone = Time.zone.now.formatted_offset(colon=false)
    start_strp = DateTime.strptime(params[:time][:start] + zone, '%m/%d/%Y %I:%M %p%Z').utc.to_s(:db)
    end_strp = DateTime.strptime(params[:time][:end] + zone, '%m/%d/%Y %I:%M %p%Z').utc.to_s(:db)

    raw_data = JSON.parse(Coordinate.select("latitude, longitude").
      where("time >= '#{start_strp}' and time <= '#{end_strp}'").to_json)
    raw_data.each do |d|
      d["count"] = "1"
    end
    # formatting for javascript
    @data = raw_data.to_json.gsub('"', '').gsub('latitude', 'lat').gsub('longitude', 'lng')
    @size = raw_data.count

    render 'stats'
  end

  def show
    @business = Business.find(params[:id])
		@deals = @business.deals.find(:all, conditions: {isEvent: false}).paginate(page: params[:deals_page], per_page: 5)
    @events = @business.deals.find(:all, conditions: {isEvent: true}).paginate(page: params[:events_page], per_page: 5)
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
