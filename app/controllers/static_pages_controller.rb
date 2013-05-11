class StaticPagesController < ApplicationController
	before_filter :session_created, only: [:home]

  def home
		@deal = current_business.deals.build if signed_in?
  end

  def help
  end

	def about
	end

	def contact
	end
	
	def stats
	end
end
