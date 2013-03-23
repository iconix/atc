class BusinessesController < ApplicationController

  def show
    @business = Business.find(params[:id])
		@deals = Deal.find(1)
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
end
