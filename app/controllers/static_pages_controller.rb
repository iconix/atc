class StaticPagesController < ApplicationController
  def home
		render :layout =>false
		@deal = current_business.deals.build if signed_in?
  end

  def help
  end

	def about
	end

	def contact
	end
end
