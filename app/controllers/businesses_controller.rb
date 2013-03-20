class BusinessesController < ApplicationController

  def show
    @business = Business.find(params[:id])
  end

  def new
    @business = Business.new
  end

  def create
    @business = Business.new(params[:business])
    if @business.save
      flash[:success] = "Welcome to Around the Corner!"
      redirect_to @business
    else
      render 'new'
    end
  end
end
