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

	def show
	  if current_business.admin? then
	    if (params[:controller] == 'businesses') then
  	    @business = Business.find(params[:id])
  	    @control = 'businesses'
  	  else
  	    @business = WebBusiness.find(params[:id])
  	    @control = 'web_businesses'
  	  end
	  else
  		@business = current_business
  		@control = 'businesses'
    end
    
    @deal = @business.deals.find(params[:id])
	end

	def destroy
	end

  def edit
    @deal = self.show
  end

  def update
    unless params[:cancel].blank?
      redirect_to :action => "show"
      return
    end
    
    @deal = self.show
    
    if @deal.update_attributes(params[:deal])
      flash[:success] = "Deal updated"
      redirect_to :action => "show"
    else
      render 'edit'
    end
  end
end
