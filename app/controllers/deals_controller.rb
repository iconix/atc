class DealsController < ApplicationController
	before_filter :signed_in_business
	before_filter :correct_user,	only: [:edit, :update, :destroy]
	
	def new
		@deal = current_business.deals.build if signed_in?
	end

	def create
		unless params[:cancel].blank?
      redirect_to current_business
      return
    end
    
	  format_params = format_params(params[:deal])
		@deal = current_business.deals.build(format_params)
    if @deal.save
      flash[:success] = "Promotion created!"
			redirect_to current_business
    else
			render 'new'
    end
	end

	def show
	  @deal = Deal.find(params[:id])
	end

	def destroy   
	  Deal.find(params[:id]).destroy
    flash[:success] = "Promotion destroyed."
    redirect_to :back
	end

  def edit
    @deal = Deal.find(params[:id])
  end

  def update
    unless params[:cancel].blank?
      redirect_to :action => "show"
      return
    end
    
    @deal = Deal.find(params[:id])
    
    format_params = format_params(params[:deal])
    if @deal.update_attributes(format_params)
      flash[:success] = "Promotion updated"
      redirect_to :action => "show"
    else
      render 'edit'
    end
  end

	private
		def correct_user
			unless current_business.admin?
				@deal = current_business.deals.find_by_id(params[:id])
				redirect_to(root_path) if (@deal.nil?)
			end
		rescue
			redirect_to :back
		end
		
		def format_params(params)
		  params[:endDate] = format_datetime(params[:endDate])
		  params[:startDate] = format_datetime(params[:startDate])
	    return params
		end
		
		def format_datetime(datetime)
		  date, time = datetime.split
		  month, day, year = date.split('/')
		  format_date = year + '-' + month + '-' + day
		  format_datetime = format_date + 'T' + time
		  return format_datetime
		end
end
