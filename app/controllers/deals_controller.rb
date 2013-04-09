class DealsController < ApplicationController
	before_filter :signed_in_business
	
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

	def destroy
	end
end
