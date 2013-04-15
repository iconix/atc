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

  def edit
    @deal = current_business.deals.find(params[:id])
  end

  def update
    unless params[:cancel].blank?
      redirect_to current_business
      return
    end
    @deal = current_business.deals.find(params[:id])
    if @deal.update_attributes(params[:deal])
      flash[:success] = "Deal updated"
      redirect_to current_business
    else
      render 'edit'
    end
  end
end
