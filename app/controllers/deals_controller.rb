class DealsController < ApplicationController
	before_filter :signed_in_business
	before_filter :correct_user only :destroy
	
	def new
		@deal = current_business.deals.build if signed_in?
	end

	def create
		@deal = current_business.deals.build(params[:deal])
    if @deal.save
      flash[:success] = "Deal created!"
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
    flash[:success] = "Deal destroyed."
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
    
    if @deal.update_attributes(params[:deal])
      flash[:success] = "Deal updated"
      redirect_to :action => "show"
    else
      render 'edit'
    end
  end

	private
	
		def correct_user
			@deal = current_business.deals.find_by_id(params[:id])
			redirect_to :back if @deal.nil?
		end
end
